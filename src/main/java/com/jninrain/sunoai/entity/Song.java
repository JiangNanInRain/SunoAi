package com.jninrain.sunoai.entity;

import com.sun.org.apache.xpath.internal.axes.AttributeIterator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/16:33
 * @Description:
 */
@Data
@Table(name = "Song")
public class Song {

    @ApiModelProperty("歌曲Id")
    private String id;

    @ApiModelProperty("视频链接")
    private String video_url;

    @ApiModelProperty("用户Id")
    private String user_id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("音频链接")
    private String audio_url;

    @ApiModelProperty("播放量")
    private  int play_count;

    @ApiModelProperty("图片链接")
    private String image_url;

    @ApiModelProperty("点赞数")
    private int upvote_count;

    @ApiModelProperty("大图片链接")
    private String image_large_url;

    @ApiModelProperty("是否公开")
    private boolean is_public;

    @ApiModelProperty("主模型版本")
    private String major_model_version;

    @ApiModelProperty("创建时间")
    private Date created_at;

    @ApiModelProperty("歌词")
    private String lyrics;

    @ApiModelProperty("歌曲时长")
    private Double duration;
}
