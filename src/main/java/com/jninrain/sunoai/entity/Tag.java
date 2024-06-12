package com.jninrain.sunoai.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/16:33
 * @Description:
 */
@Data
@Table(name = "Tags")
public class Tag {
    @Id
    @ApiModelProperty("ID主键")
    private Integer id;

    @ApiModelProperty("标签内容")
    private String content;

}
