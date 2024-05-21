package com.jninrain.sunoai.util.Result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jninrain.sunoai.exception.ServiceException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel
public class Result<T> {

    @ApiModelProperty("(0 参数错误)(1 返回成功)(2 登陆失败)(3 返回失败)(4 其他错误)(5 token过期)(6 文件未准备好)")
    private int code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty
    private T data;

    /**
     * 调用provider，如果成功则返回数据，如果不成功，则抛出异常
     * martin
     * 2020-03-16 16:03
     */
    @JsonIgnore
    public T getValidData() {
        if (code == 1) {
            return getData();
        }
        throw new ServiceException(message);
    }
}