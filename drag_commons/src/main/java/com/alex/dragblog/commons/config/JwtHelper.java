package com.alex.dragblog.commons.config;

import com.alex.dragblog.commons.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 *description:
 * jwt json web token 经常用在跨域身份验证
 * JWT由三部分构成：头部（header），载荷（payload）和签名（signature）。
 * 随着技术的发展，分布式web应用的普及，通过session管理用户登录状态成本越来越高，因此慢慢发展成为token的方式做登录身份校验，然后通过token去取redis中的缓存的用户信息，随着之后jwt的出现，校验方式更加简单便捷化，无需通过redis缓存，而是直接根据token取出保存的用户信息，以及对token可用性校验，单点登录更为简单。
 *
 *
 * 作者：aishenla
 * 链接：https://www.jianshu.com/p/fe67b4bb6f2c
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *author:       alex
 *createDate:   2020/7/4 9:15
 *version:      1.0.0
 */
@Component
public class JwtHelper {

    /**
     * description :解析jwt
     * author :     alex
     * @param :
     * @return :
     */
    public Claims parseJWT(String token, String base64Security) {
        Claims claims = null;
        try {
               claims = Jwts.parser()
                       .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                       .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * description :构建jwt
     * author :     alex
     * @param :
     * @return :
     */
    public String createJWT(String username, String adminId, String roleName, String audience, String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now =  new Date(nowMillis);
        //生成密钥
        byte[] apiKeySecreteBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key sianingKey = new SecretKeySpec(apiKeySecreteBytes, signatureAlgorithm.getJcaName());
        //添加构成jwt参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("adminId", adminId)
                .claim("role", roleName)
                .claim("createTime", now)
                .setSubject(username)
                .setIssuer(issuer)
                .setAudience(adminId)
                .signWith(signatureAlgorithm, sianingKey);
        //设置token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成jwt
        return builder.compact();
    }

    //判断token是否过期
    public Boolean isExpiration(String token, String base64Security) {
        if (parseJWT(token, base64Security) == null)
            return null;
        else
            return parseJWT(token, base64Security).getExpiration().before(new Date());
    }

    //校验token
    public Boolean validateToken(String token, UserDetails userDetails, String base64Security) {
        SecurityUser securityUser = (SecurityUser) userDetails;
        final String username = getUsername(token, base64Security);
        final boolean isExpiration = isExpiration(token, base64Security);
        return username.equals(securityUser.getUsername()) && !isExpiration;
    }

    //从token中获取用户名
    public String getUsername(String token, String base64Security) {
        return parseJWT(token, base64Security).getSubject();
    }

    //从token中获取用户UID
    public String getUserId(String token, String base64Security) {
        return parseJWT(token, base64Security).get("adminId", String.class);
    }

    //从token中获取audience
    public String getAudience(String token, String base64Security) {
        return parseJWT(token, base64Security).getAudience();
    }

    //从token中获取issuer
    public String getIssuer(String token, String base64Security) {
        return parseJWT(token, base64Security).getIssuer();
    }

    //从token中获取issuer
    public Date getExpiration(String token, String base64Security) {
        return parseJWT(token, base64Security).getExpiration();
    }

    //token是否可以更新
    public Boolean canTokenBeRefreshed(String token, String base64Security) {
        return !isExpiration(token, base64Security);
    }

    //更新token
    public String refreshToken(String token, String base64Security, long TTLMillis) {
        String refreshedToken;
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            // 生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            final Claims claims = parseJWT(token, base64Security);
            claims.put("creatDate", new Date());
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .setClaims(claims)
                    .setSubject(getUsername(token, base64Security))
                    .setIssuer(getIssuer(token, base64Security))
                    .setAudience(getAudience(token, base64Security))
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp).setNotBefore(now);
            }
            refreshedToken = builder.compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}
