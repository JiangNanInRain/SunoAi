package com.jninrain.sunoai.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/10:12
 * @Description:
 */

@Data
public class LyricsVO {
    @ApiModelProperty("歌词标题")
    private String title;

    @ApiModelProperty("歌词")
    private String lyrics;
}
