package com.alex.dragblog.base.holder;

import com.alex.dragblog.base.global.BaseSysConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *description:  requestHolder
 *author:       alex
 *createDate:   2020/6/28 22:10
 *version:      1.0.0
 */
@Slf4j
public class RequestHolder {

    /**
     * description :获取request
     * author :     alex
     * @param :
     * @return :
     */
    public static HttpServletRequest getRequest() {
        log.debug("getRequest -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            return null;
        return attributes.getRequest();
    }

    /**
     * description :获取Response
     * author :     alex
     * @param :
     * @return :
     */

    public static HttpServletResponse getResponse() {
        log.debug("getResponse -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            return null;
        return attributes.getResponse();
    }

    /**
     * description :获取Session
     * author :     alex
     * @param :
     * @return :
     */
    public static HttpSession getSession() {
        log.debug("getSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        HttpServletRequest request = null;
        if (null == (request = getRequest()))
            return null;
        return request.getSession();
    }

    /**
     * 获取session的Attribute
     *
     * @param name session的key
     * @return Object
     */
    public static Object getSession(String name) {
        log.debug("getSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes)
            return null;
        return servletRequestAttributes.getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 添加session
     *
     * @param name
     * @param value
     */
    public static void setSession(String name, Object value) {
        log.debug("setSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes)
            return;
        servletRequestAttributes.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 清除指定session
     *
     * @param name
     * @return void
     */
    public static void removeSession(String name) {
        log.debug("removeSession -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes)
            return;
        servletRequestAttributes.removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 获取所有session key
     *
     * @return String[]
     */
    public static String[] getSessionKeys() {
        log.debug("getSessionKeys -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes)
            return null;
        return servletRequestAttributes.getAttributeNames(RequestAttributes.SCOPE_SESSION);
    }

    public static String getAdminId() {
        HttpServletRequest request = RequestHolder.getRequest();
        if(request.getAttribute(BaseSysConf.ADMIN_ID) == null)
            return null;
        return request.getAttribute(BaseSysConf.ADMIN_ID).toString();
    }
}
