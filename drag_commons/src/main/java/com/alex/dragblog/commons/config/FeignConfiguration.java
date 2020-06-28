package com.alex.dragblog.commons.config;

import com.alex.dragblog.commons.config.feign.FeignBasicAuthRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

/**
 *description:  feign配置类
 *author:       alex
 *createDate:   2020/6/28 21:34
 *version:      1.0.0
 */
@Configuration
public class FeignConfiguration {

    /**
     * description :http基本认证
     * author :     alex
     * @param :
     * @return :
     */

    @Bean
    public BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
        return new BasicAuthenticationInterceptor("user", "password123");
    }

    /**
     * description :feign请求拦截器
     * author :     alex
     * @param :
     * @return :
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

}
