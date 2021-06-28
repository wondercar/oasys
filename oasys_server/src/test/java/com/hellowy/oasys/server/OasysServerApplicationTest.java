package com.hellowy.oasys.server;

import com.hellowy.oasys.server.utils.JWTTokenUtil;
import com.hellowy.oasys.server.mapper.AdminMapper;
import com.hellowy.oasys.server.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: WondercarWy
 * @since: 2021-04-28
 */
@SpringBootTest
public class OasysServerApplicationTest {
    @Autowired
    private AdminMapper mapper;
    @Autowired
    private JWTTokenUtil jwt;
    @Test
    public void testPasswordEncoder() {
        // 测试密码加密方法
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123456");
        System.out.println(encode);
        boolean result = pw.matches("123456", encode);
        System.out.println(result);
    }
    @Test
    public void testGenerateJWT(){
        // 测试token生成方法
        Admin admin = new Admin();
        admin.setUsername("zs");
        String result = jwt.generateToken(admin);
    }
}
