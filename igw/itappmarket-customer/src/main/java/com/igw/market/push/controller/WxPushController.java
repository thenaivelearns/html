package com.igw.market.push.controller;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.PageUtil;
import com.igw.market.push.vo.DividendNoticeVO;
import com.igw.market.push.vo.UserDividendPushVO;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.FundDividendDTO;
import com.igw.market.query.domain.dto.UserDividendPushDTO;
import com.igw.market.query.domain.dto.UserFund;
import com.igw.market.query.service.FundDividendService;
import com.igw.market.query.service.UserFundService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: WxPushController
 * Description：微信推送controller
 * Author: zhubengang
 * Date: Created in 2021/8/17 17:21
 * Version: 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/wx_push")
public class WxPushController {


    @Autowired
    private FundDividendService fundDividendService;

    @Autowired
    private UserFundService userFundService;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Author os-zhubg
     * @Description TODO  分红通知列表
     * @Date 2021/8/17 17:25
     * @Param []
     * @return com.igw.market.common.domain.ResultMessage
     **/
    @ApiOperation(value = "分红通知列表")
    @PostMapping(value = "dividend_notice_list/v1")
    public ResultMessage dividendNoticeList(HttpServletRequest request, @RequestBody DividendNoticeVO dividendNoticeVO) {
        String userName = (String) request.getSession().getAttribute("userName");
        String systemId = (String) request.getSession().getAttribute("systemId");
        log.info("当前用户:{}",userName);
        PageUtil<FundDividendDTO> pageUtil = fundDividendService.searchFundDividend(userName,systemId, dividendNoticeVO);
        return ResultMessage.ok(pageUtil);
    }

    @ApiOperation(value = "查询分红通知")
    @PostMapping(value = "get_dividend_notice_info/v1")
    public ResultMessage getDividendNotice(@RequestBody FundDividend fundDividend) {
        List<FundDividendDTO> fundDividends = fundDividendService.queryFundDividend(fundDividend);
        FundDividend fundDividendDTO = null;
        if (fundDividends != null && fundDividends.size() > 0){
            fundDividendDTO = fundDividends.get(0);
        }
        return ResultMessage.ok(fundDividendDTO);
    }

    /**
     * @Author os-zhubg
     * @Description   微信用户推送列表
     * @Date 2021/8/23 8:50
     * @Param [userPushVO]
     * @return com.igw.market.common.domain.ResultMessage
     **/
    @ApiOperation(value = "微信用户推送列表")
    @PostMapping(value = "user_push_list/v1")
    public ResultMessage userPushList(@RequestBody UserPushVO userPushVO) throws Exception{
        return ResultMessage.ok(userFundService.userPushList(userPushVO));
    }


    @ApiOperation(value = "测试用户")
    @PostMapping(value = "get_test_user/v1")
    public ResultMessage getTestUser() throws Exception{
        List<UserFund> userDividendPushDTOS = userFundService.getTestUser();
        return ResultMessage.ok(userDividendPushDTOS);
    }

    @ApiOperation(value = "测试用户推送信息")
    @PostMapping(value = "user_push_test/v1")
    public ResultMessage userPushTest(@RequestBody UserDividendPushVO userDividendPushVO) throws Exception{
        return userFundService.userPushTest(userDividendPushVO);
    }


    @PostMapping(value = "save_token")
    public ResultMessage saveToken (@RequestBody Map<String,String> map) throws Exception{
        String accessToken = map.get("accessToken");
        redisTemplate.opsForValue().set("wx65c246e87c5d68c5_26e833269c5546d0646a44b29f80c5f8",accessToken,7000, TimeUnit.SECONDS);
        return ResultMessage.ok(redisTemplate.opsForValue().get("wx65c246e87c5d68c5_26e833269c5546d0646a44b29f80c5f8").toString());
    }


}
