package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/17:15
 * @Description:
 */
@Data
@Table(name = "Song_Tags")
public class Song_Tags {
    @ApiModelProperty("歌曲Id")
    private String song_id;

    @ApiModelProperty("标签Id")
    private Integer tag_id;
}
