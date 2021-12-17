package com.wencoder.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * <p>
 * Created by 王林 on 2021-01-14 11:01:06
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页结果", description = "分页结果")
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = -6868692026104831795L;

    @ApiModelProperty(value = "总记录条", required = true, example = "99999")
    private Long total;

    @ApiModelProperty(position = 2, value = "分页列表")
    private List<T> array;

}
