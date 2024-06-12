package com.jninrain.sunoai.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/23/10:03
 * @Description:
 */
@Data
public class SongVO {
    @ApiModelProperty("歌曲Id")
    private String song_id;

    @ApiModelProperty("用户Id")
    private String user_id;

    @ApiModelProperty("音乐标题")
    private String title;

    @ApiModelProperty("模型版本")
    private String model_version;

    @ApiModelProperty("创建时间")
    private Date created_time;

    @ApiModelProperty("风格标签")
    private String[] tags;

    @ApiModelProperty("生成状态")
    private String status;

    @ApiModelProperty("视频链接")
    private String video_url;

    @ApiModelProperty("音频链接")
    private String audio_url;

    @ApiModelProperty("图片链接")
    private String image_url;

    @ApiModelProperty("大图片链接")
    private String image_large_url;

    @ApiModelProperty("时长s")
    private Double duration;

    @ApiModelProperty("歌词")
    private String lyrics;

//    @ApiModelProperty("错误类型")
//    private String error_type;
//
//    @ApiModelProperty("错误信息")
//    private String error_message;
}
