package com.igw.market.push.vo;

import com.igw.market.query.domain.dto.UserFund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: UserDividendPushVO
 * Description：
 * Author: zhubengang
 * Date: Created in 2021/9/14 16:50
 * Version: 1.0.0
 */
@ApiModel(value = "测试推送请求参数")
@Data
public class UserDividendPushVO extends UserFund {
    @ApiModelProperty(value = "分红模板id")
    private String dividendId;

    @ApiModelProperty(name = "sentDate", value = "发送日期(年月日)")
    private String sentDate;

    @ApiModelProperty(name = "sentTime", value = "发送时间(时分)")
    private String sentTime;

    @ApiModelProperty(name = "messageBegin", value = "消息开头")
    private String messageBegin;

    @ApiModelProperty(name = "messageEnd", value = "消息结尾")
    private String messageEnd;

    @ApiModelProperty(name = "messageHref", value = "消息连接")
    private String messageHref;

    @ApiModelProperty(name = "noticeName",value = "公告名称")
    private String noticeName;

    @ApiModelProperty(value = "推送用户列表")
    private List<UserFund> userFunds;
}
