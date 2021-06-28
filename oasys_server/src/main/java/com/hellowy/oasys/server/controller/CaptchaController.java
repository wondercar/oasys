package com.hellowy.oasys.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hellowy.oasys.server.config.constant.RedisConst;
import com.hellowy.oasys.server.utils.CaptchaUtil;
import com.hellowy.oasys.server.utils.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码控制器
 *
 * @author wondercar
 * @date 2021/1/31
 */
@Slf4j
@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    // 响应头类型
    private final String CONTENT_TYPE = "image/png";
    // 图片类型
    private final String FILE_SUFFIX = "png";

    /**
     * 获取图形验证码(字符验证码)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "图形验证码生成方法")
    @GetMapping(value = "/captchaCode", produces = CONTENT_TYPE)
    public void captchaCode(HttpServletRequest request, HttpServletResponse
            response) {
        //-------------------设置响应头 begin --------------------------
        // 设置response过期时间
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, mustrevalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // 设置响应头返回类型
        response.setContentType(CONTENT_TYPE);
        //-------------------设置响应头 begin --------------------------
        //-------------------生成验证码 begin --------------------------
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码：" + text);
        // 将验证码放入session
        request.getSession().setAttribute(RedisConst.CAPTCHA_KEY, text);
        // 根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 输出流输出图片，格式png
            ImageIO.write(image, FILE_SUFFIX, outputStream);
            // 清空缓存
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end ----------------------------
    }

    /**
     * 获取图形验证码(算数验证码)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "算数验证码生成方法")
    @GetMapping("/captchaNumber")
    public Map captchaNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头
        CaptchaUtil.setHeader(response);
        //使用算数验证码，运算位数为2
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(115, 40);
        captcha.setLen(2);
        String captchaResult = captcha.text();
        //存入缓存的key
        String captchaUuid = IdUtil.getUUID();
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("captcha", captcha.toBase64());
            put("captchaUuid", captchaUuid);
        }};
        request.getSession().setAttribute(RedisConst.CAPTCHA_KEY, captchaResult);
        log.info("验证码结果为：" + captchaResult);
        return map;
    }
}
