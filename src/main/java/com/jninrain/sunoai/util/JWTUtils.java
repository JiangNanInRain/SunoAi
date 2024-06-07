package com.jninrain.sunoai.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static String SIGNATURE = "token!@#$%^7890" ;

    /**
     * 生成token
     * @param payload //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> payload){
        JWTCreator.Builder builder = JWT.create();
        payload.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,30);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
}
