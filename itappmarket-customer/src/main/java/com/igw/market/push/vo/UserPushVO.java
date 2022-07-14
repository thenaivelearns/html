package com.igw.market.push.vo;

import com.igw.market.common.domain.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: UserPushVO
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/8/18 9:04
 * Version: 1.0.0
 */
@ApiModel(value = "用户推送请求参数")
@Data
public class UserPushVO extends PageParam {

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "基金代码")
    private String fundCode;

    @ApiModelProperty(name = "filterAmount", value = "筛选金额")
    private String filterAmount;

    @ApiModelProperty(value = "微信代码 1 生产 2 测试")
    private String wechatCode;

    @ApiModelProperty(value = "基金代码列表")
    public List<String> fundCodes;

    @ApiModelProperty(value = "openIdList")
    public List<String> openIdList;

    @ApiModelProperty(value = "t日期")
    private String tDate;
}
