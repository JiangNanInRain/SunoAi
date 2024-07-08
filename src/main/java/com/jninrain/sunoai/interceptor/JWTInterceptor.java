package com.jninrain.sunoai.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jninrain.sunoai.util.JWTUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * JWT验证拦截器
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        //如果请求为 OPTIONS 请求，则返回 true,否则需要通过jwt验证
//        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
//            System.out.println("OPTIONS请求，放行");
//            return true;
//        }
//        Map<String,Object> map = new HashMap<>();
//       //令牌建议是放在请求头中，获取请求头中令牌
//        String token = request.getHeader("token");
//        System.out.println("Token ："+token);
//      try{
//           JWTUtils.verify(token);//验证令牌
//          System.out.println("token通过");
//          return true;//放行请求
//       } catch (SignatureVerificationException e) {
//           e.printStackTrace();
//            map.put("msg","无效签名");
//          System.out.println("无效签名");
//      } catch (TokenExpiredException e) {
//            e.printStackTrace();
//            map.put("msg","token过期");
//          System.out.println("token 过期");
//        } catch (AlgorithmMismatchException e) {
//            e.printStackTrace();
//           map.put("msg","token算法不一致");
//          System.out.println("token算法不一致");
//       } catch (Exception e) {
//           e.printStackTrace();
//            map.put("msg","未授权登录");
//          System.out.println("未授权登录");
//       }
//        map.put("code",2);
//       //将map转化成json，response使用的是Jackson
//        String json = new ObjectMapper().writeValueAsString(map);
//       response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().print(json);
//        return false;
    }
}
