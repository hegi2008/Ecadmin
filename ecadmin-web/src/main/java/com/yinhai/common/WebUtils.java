package com.yinhai.common;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class WebUtils extends org.springframework.web.util.WebUtils {

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
    }

    public static String removeSpecString(String str) throws Exception {
        if (str == null) {
            return str;
        }
        String regEx = "[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s\\/]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String removeSpecStringSigle(String str) throws Exception {
        if (str == null) {
            return str;
        }
        String regEx = "[\\[\\]<>\\s\\/]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String scriptFilter(String str) {
        if (str != null && !"".equals(str)) {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            Pattern p = Pattern.compile(regEx_script);
            Matcher m = p.matcher(str);
            return m.replaceAll(" ").trim();
        } else {
            return str;
        }
    }

    /**
     * @param request
     * @return
     * @Description: 获得去掉script标签的参数
     */
    public static Map getScriptFilterParam(HttpServletRequest request) throws Exception {
        Map param = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            param.put(name, WebUtils.scriptFilter(request.getParameter(name)));
        }
        return param;
    }

    /**
     * @param object
     * @param response
     * @throws IOException
     * @Description: write json
     */
    public static void write(Object object, HttpServletResponse response) throws IOException {
        String result = "";
        if (object instanceof Map || object instanceof List) {
            result = JSON.toJSONString(object);
        } else if (object instanceof String || object instanceof StringBuffer || object instanceof StringBuilder) {
            result = object.toString();
        } else {
            result = JSON.toJSONString(object);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
    }

    /**
     * @param fileName
     * @return 文件后缀名
     * @Description: 获得文件后缀名
     */
    public static String getFileSuffix(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
		/*String name = fileName.substring(0, fileName.lastIndexOf("."));
		if(name.length() > 30) {
			name = name.substring(0, 30);
		}*/
        return "_yh_ec" + suffix;
    }

    /**
     * @param fileSuffix
     * @return
     * @Description: 生成不重复的文件名
     */
    public static String createFileName(String fileSuffix) {
        return System.currentTimeMillis() + fileSuffix;
    }

    public static void main(String[] args) {
        System.out.println(getFileSuffix("yyy.adb"));
    }
}
