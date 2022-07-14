package com.igw.market.push.service;


import com.igw.market.common.domain.AccessToken;
import com.igw.market.query.domain.dto.UserDividendPushDTO;
import net.sf.json.JSONObject;

import java.util.List;

/**
 *  工具接口
 */
public interface UtilService {

    /**
     * 获取toklen
     * @return
     */
    AccessToken getToken();


    /**
     * @Author os-zhubg
     * @Description 推送微信消息
     * @Date 2021/9/14 15:07
     * @Param []
     * @return void
     **/
    void pushMessage(List<UserDividendPushDTO> userDividendPushDTOS,String operatorType) throws Exception;

    JSONObject push(String userName, String openId, String noticeName, String messageHref, String essageBegin, String getMessageEnd,String isRetry);

}
