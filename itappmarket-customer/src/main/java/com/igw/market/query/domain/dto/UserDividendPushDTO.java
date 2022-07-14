package com.igw.market.query.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: UserDividendPushDTO
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/8/25 11:10
 * Version: 1.0.0
 */
@Data
public class UserDividendPushDTO {

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

    private UserFund userFund;


}
