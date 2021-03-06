package com.alex.dragblog.admin.config;

import com.alex.dragblog.base.handler.HandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:byteblogs
 * @Date:2018/09/27 12:52
 */
@Configuration
public class GlobalExceptionConfig {

    @Bean
    public HandlerExceptionResolver getHandlerExceptionResolver() {
        return new HandlerExceptionResolver();
    }
}
