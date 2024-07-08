package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.entity.User;
import com.jninrain.sunoai.request.RegisterRequest;
import com.jninrain.sunoai.service.UserService;
import com.jninrain.sunoai.util.JWTUtils;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("登录")
    public Result login( String userName, String password){

        String pwd = userService.getPwdByUserName(userName);
        if(null==pwd){
            return ResultUtil.fail("用户不存在");
        }
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
                return ResultUtil.fail("登陆失败");
            }
            return ResultUtil.ok(token);
        }else {
            return ResultUtil.fail("密码错误或用户不存在");
        }

    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody RegisterRequest req){
        if(null!=userService.getIdByUserName(req.getUserName())){
            return ResultUtil.fail("用户已存在");
        }
        User user = new User();
        user.setPassword(req.getPassword());
        user.setHandle(req.getUserName());
        user.setEmail(req.getEmail());
        user.setDisplay_name(req.getUserName());
        boolean flag = userService.save(user);


        return flag? ResultUtil.ok("注册成功"):ResultUtil.fail("注册失败");
    }
}
