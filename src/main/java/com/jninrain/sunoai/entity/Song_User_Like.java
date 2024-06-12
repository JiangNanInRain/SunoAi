package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/15:12
 * @Description:
 */
@Data
@Table(name = "Song_User_Like")
public class Song_User_Like {
    @ApiModelProperty("歌曲Id")
    private String song_id;

    @ApiModelProperty("用户Id")
    private String user_id;
}
