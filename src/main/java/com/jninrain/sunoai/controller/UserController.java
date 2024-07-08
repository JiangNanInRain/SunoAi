package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.entity.User;
import com.jninrain.sunoai.service.UserService;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.TokenParseUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/07/08/16:00
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;
    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpServletRequest httpServletRequest){
        String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");

        User user = userService.getUserInfoByUserId(Long.parseLong(user_id));


        return ResultUtil.ok(user);
    }

    @ApiModelProperty("改密码")
    @GetMapping("/pwdChange")
    public Result pwdChange(String userName,String pwd ){

        userService.updatePwd(userName,pwd);


        return ResultUtil.ok();
    }

    @ApiModelProperty("根据名字查邮箱")
    @GetMapping("/queryEmailByName")
    public String queryEmailByName(String userName){

       String  email =  userService.queryEmailByName(userName);


        return email;
    }
}
