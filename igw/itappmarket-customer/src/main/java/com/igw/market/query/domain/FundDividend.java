package com.igw.market.query.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ApiModel(value = "基金分红")
public class FundDividend {
    @ApiModelProperty(name = "sysId", value = "主键")
    private String sysId;
    @ApiModelProperty(name = "sentDate", value = "发送日期(年月日)")
    private String sentDate;
    @ApiModelProperty(name = "sentTime", value = "发送时间(时分)")
    private String sentTime;
    @ApiModelProperty(name = "filterAmount", value = "筛选金额")
    private String filterAmount;
    @ApiModelProperty(name = "messageBegin", value = "消息开头")
    private String messageBegin;
    @ApiModelProperty(name = "fundName", value = "基金名称")
    private String fundName;
    @ApiModelProperty(name = "fundCode", value = "基金代码")
    private String fundCode;
    @ApiModelProperty(name = "messageEnd", value = "消息结尾")
    private String messageEnd;
    @ApiModelProperty(name = "messageHref", value = "消息连接")
    private String messageHref;
    @ApiModelProperty(name = "noticeName",value = "公告名称")
    private String noticeName;
    @ApiModelProperty(name = "promptType", value = "提示类型")
    private String promptType;
    @ApiModelProperty(name = "sentStatus", value = "发送状态 1 未发送  2  已发送")
    private String sentStatus;
    @ApiModelProperty(name = "reviewStatus", value = "复核状态  1 待复核 2  复核通过")
    private String reviewStatus;
    @ApiModelProperty(name = "createdUser", value = "创建人")
    private String createdUser;
    @ApiModelProperty(name = "createdDate", value = "创建时间")
    private Date createdDate;
    @ApiModelProperty(name = "updatedUser", value = "修改人")
    private String updatedUser;
    @ApiModelProperty(name = "updatedDate", value = "修改时间")
    private Date updatedDate;
    @ApiModelProperty(name = "validFlag", value = "是否有效", notes = "0:有效,1:无效")
    private String validFlag;

}
