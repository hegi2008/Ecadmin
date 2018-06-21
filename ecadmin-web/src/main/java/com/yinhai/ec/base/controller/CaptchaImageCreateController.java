package com.yinhai.ec.base.controller;

import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
* @package com.yinhai.ec.base.controller
* <p>Title: CaptchaImageCreateController.java</p>
* <p>Description: 生成验证码 依赖Captcha验证码插件</p>
* @author cjh
* @date 2016年4月25日 下午1:35:52
* @version 1.0
 */

@Controller
public class CaptchaImageCreateController extends BaseController implements InitializingBean{
	private DefaultKaptcha captchaProducer = null;  
	  
    @RequestMapping(value="images/kaptcha.jpg",method=RequestMethod.GET)
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {  
    	  
        response.setDateHeader("Expires", 0);  
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg  
        response.setContentType("image/jpeg");  
        // create the text for the image  
        String capText = captchaProducer.createText();  
        // store the text in the session  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		captchaProducer = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.border.color", "105,179,90");
		properties.setProperty("kaptcha.textproducer.font.color", "red");
		properties.setProperty("kaptcha.image.width", "250");
		properties.setProperty("kaptcha.image.height", "90");
		properties.setProperty("kaptcha.textproducer.font.size", "75");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		properties.setProperty("kaptcha.noise.color", "black");
		properties.setProperty("kaptcha.background.clear.from", "white");
		properties.setProperty("kaptcha.background.clear.to", "white");
		Config config = new Config(properties);
		captchaProducer.setConfig(config);
	}
}
