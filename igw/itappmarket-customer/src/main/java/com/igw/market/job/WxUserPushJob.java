package com.igw.market.job;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.handler.CalendarHandler;
import com.igw.market.push.service.UtilService;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.dao.FundDividendDao;
import com.igw.market.query.dao.UserFundDao;
import com.igw.market.query.domain.dto.UserDividendPushDTO;
import com.igw.market.query.service.FundDividendService;
import com.igw.market.query.service.UserFundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * ClassName: WxUserPushJob
 * Description： 推送微信消息
 * Author: zhubengang
 * Date: Created in 2021/8/23 17:19
 * Version: 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/user_push")
@Api(tags = "定时任务 ===> 推送微信消息")
public class WxUserPushJob {

    @Value("${weixin.appid}")
    public  String APPID;

    @Value("${weixin.appsecret}")
    public String APPSECRET ;

    @Value("${dividend.notice.template}")
    public String dividendNotice;

    @Value("${wechat_code}")
    public String wechatCode;

    @Autowired
    private FundDividendService fundDividendService;

    @Autowired
    private UserFundDao userFundDao;

    @Autowired
    private FundDividendDao fundDividendDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UtilService utilService;

    @Autowired
    private CalendarHandler calendarHandler;

    @Autowired
    private UserFundService userFundService;


    @ApiOperation(value = "给符合分红通知的用户推送消息")
    @PostMapping(value = "/excute/v1")
    public ResultMessage excute() throws Exception {
        log.info(new Date() + "--------------------------分红通知发送开始执行----------------------");
        try{
            UserPushVO userPushVO = new UserPushVO();
            userPushVO.setWechatCode(wechatCode);
            //获取待推送用户列表
            List<UserDividendPushDTO> userDividendPushDTOS = userFundService.getPushList(userPushVO);
            //推送微信消息
            utilService.pushMessage(userDividendPushDTOS,"1");
        }catch (Exception e){
            e.printStackTrace();
            log.error("---WxUserPushJob:推送公众号消息运行失败!---------",e);
        }
        log.info(new Date() + "---------------------------分红通知发送结束执行----------------------");
        return ResultMessage.ok();
    }

}
