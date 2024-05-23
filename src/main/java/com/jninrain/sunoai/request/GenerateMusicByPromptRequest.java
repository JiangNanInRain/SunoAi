package com.jninrain.sunoai.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/23/09:46
 * @Description:
 */
@Data
public class GenerateMusicByPromptRequest {

    @ApiModelProperty("音乐描述")
    private String prompt;

    @ApiModelProperty("是否为纯音乐")
    private Boolean isAbsoluteMusic;

    @ApiModelProperty("模型名称")
    private String model_name;
}
