package com.hellowy.oasys.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 主启动类
 *
 * @author wondercar
 * @date 2020/12/30
 */

@EnableCaching
@SpringBootApplication
@MapperScan({"com.hellowy.oasys.server.mapper"})
public class OasysServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasysServerApplication.class, args);
    }

}
