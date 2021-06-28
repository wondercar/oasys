package com.hellowy.oasys.server.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author wondercar
 * @date 2021/1/30
 */
@Component
public class JWTTokenUtil {
    //用户名的key
    private static final String CLAIM_KEY_USERNAME = "sub";
    //jwt的创建时间的key
    private static final String CLAIM_KEY_CREATED = "created";
    //jwt的密钥
    @Value("${jwt.secret}")
    private String secret;
    //jwt的失效时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载生成jwt token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                // 设置负载
                .setClaims(claims)
                // 设置过期时间
                .setExpiration(generateExpirationDate())
                // 设置签名算法及密钥
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登陆用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            // 获取负载
            Claims claims = getclaimsfromtoken(token);
            // 负载中获取用户名
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取荷载
     *
     * @param token
     * @return
     */
    private Claims getclaimsfromtoken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    // 解析密钥
                    .setSigningKey(secret)
                    // 待解析token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 验证token是否有效
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        //与当前时间作比较 超过当前时间则代表token失效
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getclaimsfromtoken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        // 获取claims
        Claims claims = getclaimsfromtoken(token);
        // 重设创建时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        // 重新生成token
        return generateToken(claims);

    }
}
