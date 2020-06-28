package com.alex.dragblog.commons.config.feign;

import com.alex.dragblog.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  Feign请求拦截器（设置请求头，传递登录信息）
 *author:       alex
 *createDate:   2020/6/28 21:38
 *version:      1.0.0
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取token
        String token = request.getParameter("token");
        if (StringUtils.isNotEmpty(token)) {
            //如果有？说明还有其他参数，我们只截取到token即可
            if (token.indexOf("?") != -1) {
                String[] params = token.split("\\?url=");
                token = params[0];
            }
            requestTemplate.header("pictureToken", token);
        }
    }
}
