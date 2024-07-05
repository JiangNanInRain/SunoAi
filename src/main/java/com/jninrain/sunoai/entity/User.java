package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/07/14:57
 * @Description:
 */
@Data
@Table(name = "User")
public class User {

    @ApiModelProperty("用户ID")
    private String uid;

    @ApiModelProperty("用户名")
    private String handle;

    @ApiModelProperty("昵称")
    private String display_name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("粉丝数")
    private Integer fans_count;

    @ApiModelProperty("播放数")
    private Integer view_count;

    @ApiModelProperty("点赞数")
    private Integer upvote_count;

    @ApiModelProperty("个人简介")
    private String description;

    @ApiModelProperty("剩余生成额度")
    private Integer limit;

    @ApiModelProperty("注册日期")
    private Date registration_time;

    @ApiModelProperty("邮箱")
    private String email;
}
