package com.yinhai.xtpt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.yinhai.ec.common.util.XMLUtil;
import com.yinhai.xtpt.cdsi.CdsiInvokeRequest;
import com.yinhai.xtpt.cdsi.CdsiInvokeResponse;
import com.yinhai.xtpt.exception.ClientException;
import com.yinhai.xtpt.exception.ServerException;
/**
* @package com.yinhai.xtpt.util
* <p>Title: XTPTInvoker.java</p>
* <p>针对ta 协同平台,非ta项目,对发布的dubbo服务的调用</p>
* @author cjh
* @date 2017年2月21日 下午2:02:18
 */
public class XTPTInvoker {
	private static final String xtptUrl = "http://192.168.18.110:8080/xtpt/cdsiService/invoke";
	private static TrustManager tm;
	private static SSLContext sslContext;
	private static SSLConnectionSocketFactory sslSocketFactory;
	private static PoolingHttpClientConnectionManager connectionManager;
	private static RequestConfig requestConfig;
	private static final Logger log = LoggerFactory.getLogger(XTPTInvoker.class);
	static{
		tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) 
					throws java.security.cert.CertificateException {
				//TODU
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				//TODU
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				//TODU
				return null;
			}
		};
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { tm }, null);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("sslContext init failed:{}", e);
			}
		}
		sslSocketFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		
		//配置PoolingHttpClientConnectionManager
		connectionManager = new PoolingHttpClientConnectionManager(
		        RegistryBuilder.<ConnectionSocketFactory>create()
		            .register("http", PlainConnectionSocketFactory.getSocketFactory())
		            .register("https", sslSocketFactory)
		            .build());
		//设置默认socket超时时间60000ms
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60000).build();
		connectionManager.setDefaultSocketConfig(socketConfig);
		//连接池上限400
		connectionManager.setMaxTotal(400);
		//每个url最多分配40
		connectionManager.setDefaultMaxPerRoute(40);
		
		//配置RequestConfig连接参数
		requestConfig = RequestConfig.custom()
				//从连接池获取连接的timeout
				.setConnectionRequestTimeout(5000)
				//和服务器建立连接的timeout
				 .setConnectTimeout(5000)
				 //从服务器读取数据的timeout
				 .setSocketTimeout(60000)
				 .build();
	}
	/**
	 * 调用dubbo服务
	 * @param yad900 接入系统标识
	 * @param yad902 交易服务编号
	 * @param businessParam 业务参数
	 * @param resultType 返回值类型 'json' 'xml'
	 * @return Object 
	 * @author cjh
	 * @throws ClientException 
	 * @throws IOException 
	 * @throws ServerException 
	 */
	public static Object invokeService(String yad900, String yad902, Object businessParam, String resultType) throws ClientException, ServerException {
		Assert.assertNotNull("接入系统标识不能为空", yad900);
		Assert.assertNotNull("交易服务编号不能为空", yad902);
		
		CdsiInvokeRequest invokeRequest = new CdsiInvokeRequest();
		invokeRequest.setYad900(yad900);
		invokeRequest.setYad901(String.valueOf(System.currentTimeMillis()));
		invokeRequest.setYad902(yad902);
		invokeRequest.setYad904("62342341231231231234");
		invokeRequest.setYad906(businessParam);
		if(resultType != null)invokeRequest.setResultType(resultType);
		
		//从连接池获取HttpClient实例
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
		//配置SerializeConfig
		SerializeConfig config = new SerializeConfig();
		SimpleDateFormatSerializer dateFormat = new SimpleDateFormatSerializer("yyyy-MM-dd"), dateTimeFormat = new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss");
		config.put(Date.class, dateFormat);
		config.put(java.util.Date.class, dateFormat);
		config.put(Timestamp.class, dateTimeFormat);
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		String bizcontent = JSON.toJSONString(invokeRequest,config, SerializerFeature.WriteMapNullValue);
		paramList.add(new BasicNameValuePair("bizcontent", bizcontent));//参数
		paramList.add(new BasicNameValuePair("sign", RSAUtils.sign(bizcontent)));//签名
		
		//组装entity
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(paramList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new ClientException("不支持UTF-8字符集!");
		}
		//使用post提交
		HttpPost post = new HttpPost(xtptUrl);
		//设置entity
		post.setEntity(entity);
		//发起请求
		CloseableHttpResponse httpResponse = null;
		int status = HttpStatus.SC_CONTINUE;
		try {
			httpResponse = httpClient.execute(post,HttpClientContext.create());
			status = httpResponse.getStatusLine().getStatusCode();
		} catch (IOException e1) {
			throw new ServerException("发起请求出错: "+e1.getMessage());
		}
		String returnStr = "";
		CdsiInvokeResponse invokeResponse = null;
		//返回正常
		if (status == HttpStatus.SC_OK) {
			//将返回内容转为string
			try {
				returnStr = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException e) {
				throw new ClientException("返回值字符转换出错");
			} catch (IOException e) {
				throw new ClientException("返回值字符转换出错");
			}
			if("json".equalsIgnoreCase(invokeRequest.getResultType())){
				invokeResponse = JSONObject.toJavaObject(JSON.parseObject(returnStr), CdsiInvokeResponse.class);
			}else if ("xml".equalsIgnoreCase(invokeRequest.getResultType())) {
				Map<String, Object> map = XMLUtil.xmlBody2map(returnStr);
				String jsonStr = JSON.toJSONString(map,config, SerializerFeature.WriteMapNullValue);
				invokeResponse = JSONObject.toJavaObject(JSON.parseObject(jsonStr), CdsiInvokeResponse.class);
			}
		}else{
			 post.abort(); 
			 if (status == 404) {
					throw new ServerException("地址错误！");
			 } else {
					throw new ServerException("服务器内部错误！");
			 }
		} 
		log.info("调用xtpt服务返回结果:{}",returnStr);
		//释放资源
		if(httpResponse!=null){
			try {
				httpResponse.close();
			} catch (IOException e) {
			}
		}
		post.releaseConnection();
		
		if (invokeResponse.getYad907().equals(CdsiInvokeResponse.INVOKE_SUCCESS)) {
			if(invokeResponse.getYad909() instanceof String){
				return invokeResponse.getYad909();
			}else if(invokeResponse.getYad909() instanceof JSONObject){
				JSONObject object = (JSONObject) invokeResponse.getYad909();
				if(object.containsKey("yad907") && object.containsKey("yad908") && object.getString("yad907").equals(CdsiInvokeResponse.INVOKE_FAIL)){
					String yad908 = (object.get("yad908")+"").trim();
					if(yad908.startsWith("<![CDATA[") && yad908.endsWith("]]>")){
						yad908 = yad908.substring(9, yad908.length()-3);
					}
					return yad908;
				}else {
					return invokeResponse.getYad909();
				}
			}else if(invokeResponse.getYad909() instanceof JSONObject){
				return invokeResponse.getYad909();
			}else {
				return invokeResponse.getYad909();
			}
		}else if (invokeResponse.getYad907().equals(CdsiInvokeResponse.INVOKE_FAIL)) {
			return invokeResponse.getYad908();
		}
		return null;
	}
	
	public static void main(String[] args) throws ServerException, ClientException, IOException {
		System.out.println(XTPTInvoker.invokeService("ecadmin","ecadmin_sayHello",new String[]{"张三","4567"},"json"));
	}
}
