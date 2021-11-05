package com.wencoder.tools.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页表单
 * <p>
 * Created by 王林 on 2021-01-14 11:01:53
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页表单", description = "从page开始分页，每一页显示limit")
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 6094298514687248499L;

    @Min(value = 1, message = "当前分页必须大于等于 1")
    @ApiModelProperty(value = "当前页", required = true, example = "1")
    private Long page = 1L;

    @ApiModelProperty(value = "显示数（默认值：10，最大值：5000）", required = true, example = "10")
    @Range(min = 1, max = 5000, message = "每页分页个数只能在1 - 5000之间")
    private Long limit = 10L;

}
