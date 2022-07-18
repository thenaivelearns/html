package com.igw.market.query.service;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.PageUtil;
import com.igw.market.push.vo.UserDividendPushVO;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.domain.dto.UserDividendPushDTO;
import com.igw.market.query.domain.dto.UserFund;

import java.util.List;

public interface UserFundService {
    /**
     * 查询用户名下的所有基金
     *
     * @return
     */
    List<UserFund> queryAllFund(UserPushVO userPushVO);


    PageUtil userPushList(UserPushVO userPushVO) throws Exception;

    ResultMessage userPushTest(UserDividendPushVO userDividendPushVO) throws Exception;

    List<UserFund> getTestUser() throws Exception;

    List<UserDividendPushDTO> getPushList(UserPushVO userPushVO) throws Exception;

    /**
     * 通过微信openId查询持有产品编码
     * @param openId
     * @return
     */
    List<String> userFundCodeByIde(String identityNo ,String identityType);
}
