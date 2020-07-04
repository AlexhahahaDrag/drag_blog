package com.alex.dragblog.commons.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *description:  jwt相关配置
 *author:       alex
 *createDate:   2020/7/4 17:20
 *version:      1.0.0
 */
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {

    private String clientId;

    private String base64Security;

    private String name;

    private int expiresSecond;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBase64Security() {
        return base64Security;
    }

    public void setBase64Security(String base64Security) {
        this.base64Security = base64Security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "clientId='" + clientId + '\'' +
                ", base64Security='" + base64Security + '\'' +
                ", name='" + name + '\'' +
                ", expiresSecond=" + expiresSecond +
                '}';
    }
}
