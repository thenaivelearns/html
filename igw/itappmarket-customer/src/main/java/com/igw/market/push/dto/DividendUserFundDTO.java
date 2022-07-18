package com.igw.market.push.dto;

import com.igw.market.query.domain.dto.UserFund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * ClassName: DividendUserFundDTO
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/9/14 9:39
 * Version: 1.0.0
 */
@Data
@ToString
@ApiModel(value = "用户推送列表返回实体")
public class DividendUserFundDTO {

    @ApiModelProperty(name = "noticeName",value = "公告名称")
    private String noticeName;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户基金列表")
    private List<UserFund> userFunds;
}
