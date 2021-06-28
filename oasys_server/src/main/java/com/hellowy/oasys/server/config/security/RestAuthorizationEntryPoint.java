package com.hellowy.oasys.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellowy.oasys.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录或者token失效时
 * 处理器
 *
 * @author wondercar
 * @date 2021/1/31
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        StringBuffer requestURL = request.getRequestURL();
        PrintWriter writer = response.getWriter();
        RespBean error = RespBean.error("您暂未登录，请先登录");
        error.setCode(401);
        writer.write(new ObjectMapper().writeValueAsString(error));
        writer.flush();
        writer.close();
    }
}
