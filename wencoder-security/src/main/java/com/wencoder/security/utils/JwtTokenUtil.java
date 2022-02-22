package com.wencoder.security.utils;


import com.wencoder.security.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT
 */
public class JwtTokenUtil {

    protected final SecurityProperties properties;
    /**
     * token加密密钥
     */
    private final String secret;

    public JwtTokenUtil(SecurityProperties properties) {
        this.properties = properties;
        this.secret = properties.getToken().getSecret();
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Integer tokenExpireTime = properties.getToken().getTokenExpireTime();
        // 没有过期时间，默认为5分钟
        tokenExpireTime = ObjectUtils.isEmpty(tokenExpireTime) ? 5 : tokenExpireTime;
        Date expirationDate = new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param username 用户
     * @return 令牌
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", username);
        claims.put("created", new Date());
        return this.generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = this.getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken = null;
        try {
            Claims claims = this.getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        //JwtUser user = (JwtUser) userDetails;
        //String username = getUsernameFromToken(token);
        //return (username.equals(user.getUsername()) && !isTokenExpired(token));
        return false;
    }


}
