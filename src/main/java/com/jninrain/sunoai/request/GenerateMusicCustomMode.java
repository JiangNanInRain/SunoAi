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

    @ApiModelProperty("模型名称")
    private String model_name;

    @ApiModelProperty("音乐风格")
    private String tags;

    @ApiModelProperty("音乐标题")
    private String title;



}
