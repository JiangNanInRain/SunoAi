package com.jninrain.sunoai.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/16:40
 * @Description:
 */

public class TokenParseUtil {

    private static String SIGNATURE;
    public static String get(String token,String property){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("token!@#$%^7890")).build().verify(token);
        return decodedJWT.getClaim(property).asString();
    }

    public static void main(String[] args) {
        System.out.println(get("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI0ZDkzZjMyNi0zZjQ4LTRhNDMtOTI5ZC1iNjQ4OWY0NzU0YjUiLCJ1c2VyTmFtZSI6ImFkbWluIiwiZXhwIjoxNzE3NzUwOTg0fQ.FGmm2GvE0kdatVUky3CYpVY-0v3FZsS_Ibc412UpAP4", "uid"));

    }
}
