package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.service.UserService;
import com.jninrain.sunoai.util.JWTUtils;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/14:40
 * @Description:
 */
@Slf4j
@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @GetMapping("/login")
    public Result login( String userName, String password){
        String pwd = userService.getPwdByUserName(userName);
        if (pwd.equals(password)){
            String token = null;
            try {
                Map<String, String> payload = new HashMap<>(2);
                payload.put("uid", userService.getIdByUserName(userName));
                payload.put("userName", userName);
                //生成Token令牌
                token = JWTUtils.getToken(payload);
                System.out.println(token);
            }catch (Exception e){
                return ResultUtil.fail("错误");
            }
            return ResultUtil.ok(token);
        }

        return ResultUtil.fail("登录失败");
    }
}
