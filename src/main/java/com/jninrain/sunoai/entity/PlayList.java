package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/17:01
 * @Description:
 */
@Data
@Table(name = "PlayList")
public class PlayList {

    @Id
    @ApiModelProperty("自增Id")
    private Integer id;

    @ApiModelProperty("用户Id")
    private String user_id;

    @ApiModelProperty("歌单名")
    private String name;

    @ApiModelProperty("歌单简介")
    private String description;

    @ApiModelProperty("歌单封面图片")
    private String image;



}
