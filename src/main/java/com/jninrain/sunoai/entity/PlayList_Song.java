package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/13/11:34
 * @Description:
 */
@Data
@Table(name="PlayList_Song")
public class PlayList_Song {

    @ApiModelProperty("歌单Id")
    private Integer playList_id;

    @ApiModelProperty("歌曲Id")
    private  String song_id;
}
