package com.jninrain.sunoai.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/07/08/20:26
 * @Description:
 */
@Data
public class PlayListVO {


    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("歌单名")
    private String name;

    @ApiModelProperty("歌单简介")
    private String description;

    @ApiModelProperty("歌单封面图片")
    private String image;

    @ApiModelProperty("歌曲数据")
    private List<SongCardVO> songs;



}
