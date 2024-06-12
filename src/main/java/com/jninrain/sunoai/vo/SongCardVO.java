package com.jninrain.sunoai.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.print.attribute.standard.PrintQuality;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/15:41
 * @Description:
 */
@Data
public class SongCardVO {

    @ApiModelProperty("小图片链接")
    private String image_url;

    @ApiModelProperty("歌曲时长")
    private  Double duration;

    @ApiModelProperty("标题")
    private String title;


    @ApiModelProperty("风格标签")
    private String[] tags;

    @ApiModelProperty("播放数")
    private Integer play_count;

    @ApiModelProperty("点赞数")
    private Integer upvote_count;

    @ApiModelProperty("歌曲Id")
    private String id;

    @ApiModelProperty("昵称")
    private String display_name;

    @ApiModelProperty("是否已点赞")
    private Boolean isLike;

}
