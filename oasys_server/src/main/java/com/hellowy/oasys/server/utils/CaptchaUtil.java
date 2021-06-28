package com.hellowy.oasys.server.utils;

import com.hellowy.oasys.server.config.constant.RedisConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图形验证码工具类
 *
 * @author wondercar
 * @date 2021/1/31
 */
public class CaptchaUtil {
    //验证码key
    private static final String SESSION_KEY = RedisConst.CAPTCHA_KEY;

    /**
     * 验证验证码
     *
     * @param code    用户输入的验证码
     * @param request HttpServletRequest
     * @return 校验结果
     */
    public static boolean ver(String code, HttpServletRequest request) {
        // 非空判断
        if (code != null) {
            // 获取session中的验证码字段
            String captcha = (String) request.getSession().getAttribute(SESSION_KEY);
            // 输入验证码转小写后与session中的验证码作比较
            return code.trim().toLowerCase().equals(captcha);
        }
        return false;
    }

    /**
     * 清除session中的验证码
     *
     * @param request HttpServletRequest
     */
    public static void clear(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_KEY);
    }

    /**
     * 设置相应头
     *
     * @param response HttpServletResponse
     */
    public static void setHeader(HttpServletResponse response) {
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

}
