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
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页结果", description = "分页结果")
public class PageResult<T> extends PageQuery implements Serializable {

    private static final long serialVersionUID = -6868692026104831795L;

    @ApiModelProperty(value = "总记录", required = true, example = "99999")
    private Long total;

    @ApiModelProperty(position = 2, value = "列表")
    private List<T> array;

}
