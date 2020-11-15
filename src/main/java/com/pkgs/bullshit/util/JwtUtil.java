package com.pkgs.bullshit.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-15 15:21
 */
public class JwtUtil {

    /**
     * 加密字符串:盐
     */
    private static String secret = "cs12110";
    /**
     * 加密算法
     */
    private static Algorithm algorithm = Algorithm.HMAC256(secret);

    /**
     * 创建token
     *
     * @param data   数据
     * @param expire 过期时间,mills
     * @return String
     */
    public static String create(Object data, long expire) {
        // 通过数据库校验用户是否通过,通过则进行token的构建
        Map<String, Object> head = new HashMap<>(8);
        head.put("alg", "HS256");
        head.put("typ", "JWT");

        return JWT.create()
            // 头部
            .withHeader(head)
            // payload
            .withClaim("payload", JSON.toJSONString(data))
            // 过时时间
            .withExpiresAt(new Date(System.currentTimeMillis() + expire))
            // 加密
            .sign(algorithm);
    }

    /**
     * 校验token
     *
     * @param token token
     * @return String
     */
    public static String verify(String token) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", 200);
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (Exception e) {
            //token过期
            e.printStackTrace();

            map.put("status", 404);
            map.put("msg", "token is illegal");
        }
        return map.toString();
    }

    /**
     * 解密
     *
     * @param token token
     * @return String
     */
    public static String decode(String token) {
        String payload = null;
        try {
            JWTVerifier build = JWT.require(algorithm).build();
            DecodedJWT verify = build.verify(token);
            Claim data = verify.getClaim("payload");

            //如果没有值,返回null
            payload = data.asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payload;
    }

    /**
     * 刷新token
     *
     * @param token  token
     * @param expire 过时时间
     * @return String
     */
    public static String refresh(String token, long expire) {
        return create(decode(token), expire);
    }
}
