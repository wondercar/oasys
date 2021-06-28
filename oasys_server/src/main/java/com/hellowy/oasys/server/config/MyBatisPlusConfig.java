package com.hellowy.oasys.server.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置类
 *
 * @author wondercar
 * @date 2020/12/29
 */
@Configuration(proxyBeanMethods = false)
public class MyBatisPlusConfig {
    /**
     * plus分页配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        //最大单页限制数量
        paginationInnerInterceptor.setMaxLimit(1000L);
        return mybatisPlusInterceptor;
    }
}
