package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.util.Email;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/07/08/23:20
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;


    @ApiOperation("发送验证码到某邮箱")
    @GetMapping("/sendEmail")
    public Result<String> sendEmail(String address){

        Random random = new Random();
        Integer code = random.nextInt(9000)+1000;
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(address);
        //邮件标题
        message.setSubject("验证码");
        //邮件内容
        message.setText("验证码为"+code);
        try {
            mailSender.send(message);
            return ResultUtil.ok(""+code);
        } catch (MailException e) {
            e.printStackTrace();
            return ResultUtil.ok("发送失败");
        }


    }
}
