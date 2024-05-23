package com.jninrain.sunoai.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/23/14:55
 * @Description:
 */
@Data
public class GenerateMusicCustomMode {

    @ApiModelProperty("音乐描述")
    private String prompt;



    @ApiModelProperty("是否为纯音乐")
    private Boolean isAbsoluteMusic;



}
