package com.wencoder.security.utils;

import com.wencoder.security.domain.DefaultUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

    static String salt = "SALT";

    static int DAY = 30;

    public static String create(DefaultUserDetails detail, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setId(detail.getId().toString())
                // 用户角色
                .claim("ROLE_LOGIN", detail.getAuthorities())
                // 主题 - 存用户名
                .setSubject(detail.getUsername())
                // 过期时间 - day天 * 24小时 * 60分钟 * 60秒 * 1000L 毫秒
                .setExpiration(new Date(System.currentTimeMillis() + DAY * 24 * 60 * 60 * 1000L))
                // 加密算法和密钥
                .signWith(SignatureAlgorithm.HS512, salt)
                .compact();
    }

    public static Claims parse(String token) {
        return Jwts.parser().setSigningKey(salt)
                .parseClaimsJws(token).getBody();
    }
}
