package com.igw.market.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author os-zhubg
 * @Description  分页对象参数
 * @Date 2021/8/18 9:01
 * @Param
 * @return
 **/
@ApiModel(description = "分页参数")
@Data
public class PageParam {
    /**
     * 每页显示数
     */
    @ApiModelProperty(value = "每页显示数",required = true)
    @NotNull(message = "分页参数缺少pageSize")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页" ,required = true)
    @NotNull(message = "分页参数缺少pageNum")
    private Integer pageNumber;

}
