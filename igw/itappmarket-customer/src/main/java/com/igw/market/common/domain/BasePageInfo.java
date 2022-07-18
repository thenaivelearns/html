package com.igw.market.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ClassName: BasePageInfo
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/3/3 14:05
 * Version: 1.0.0
 */
@ApiModel(value = "分页返回实体")
@Getter
@Setter
public class BasePageInfo<T> extends PageParam{

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private long total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long pages;

    /**
     * 每页显示的集合
     */
    @ApiModelProperty(value = "数据")
    private List<T> list;


    /**
     * @Author os-zhubg
     * @Description TODO 组装分页返回数据
     * @Date 2021/3/3 14:25
     * @Param [list, pageParam, totalCount]
     * @return com.igw.product.common.domain.BasePageInfo<T>
     **/
    public static <T>BasePageInfo<T> getPageInfo(List list,Integer pageNum, Integer pageSize){
        BasePageInfo basePageInfo = new BasePageInfo();
        if (list == null || list.size() == 0) {
            basePageInfo.setList(list);
            return basePageInfo;
        }

        //记录总数
        Integer count = list.size();
        // 页数
        Integer pageCount = 0;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        // 开始索引
        int fromIndex = 0;
        // 结束索引
        int toIndex = 0;
        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex, toIndex);
        basePageInfo.setTotal(count);
        basePageInfo.setPages(pageCount);
        basePageInfo.setList(pageList);
        return basePageInfo;
    }

}
