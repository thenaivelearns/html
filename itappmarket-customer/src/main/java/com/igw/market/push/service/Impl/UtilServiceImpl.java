package com.igw.market.push.service.Impl;


import com.igw.market.common.constant.Constant;
import com.igw.market.common.domain.AccessToken;
import com.igw.market.common.util.*;
import com.igw.market.push.service.UtilService;
import com.igw.market.query.dao.FundDividendDao;
import com.igw.market.query.domain.dto.UserDividendPushDTO;
import com.igw.market.query.domain.dto.UserFund;
import com.igw.market.query.domain.vo.FundDividendVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UtilServiceImpl implements UtilService {


    //凭证获取（GET）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Value("${access_token.address}")
    public String access_token_address;

    @Value("${weixin.appid}")
    public  String APPID;

    @Value("${weixin.appsecret}")
    public String APPSECRET ;

    @Value("${dividend.notice.template}")
    public String dividendNotice;

    @Autowired
    private FundDividendDao fundDividendDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取token
     * @return
     */
    @Override
    public AccessToken getToken() {
        AccessToken token = null;
        String requestUrl = token_url.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        // 发起GET请求获取凭证
        ResultMessage resultMessage = HttpUtil.doGetHttp(requestUrl,null);
        if (null != resultMessage) {
            try {
                String content = resultMessage.getData().toString();
                JSONObject jsonObject = JSONObject.fromObject(content);
                token = new AccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
                //存入redis
                redisTemplate.opsForValue().set(APPID + "_" + APPSECRET,token.getAccessToken(),7000, TimeUnit.SECONDS);
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败");
            }
        }
        return token;
    }


    public AccessToken getAccessToken(){
        AccessToken token = new AccessToken();
        try {
            // 发起GET请求获取凭证
            com.igw.market.common.util.ResultMessage resultMessage = HttpUtil.doGetHttp(access_token_address,null);
            log.info("resultMessage:{}", resultMessage.toString());
            if (resultMessage != null && "Y".equals(resultMessage.getMsgCode())){
                String content = resultMessage.getData().toString();
                JSONObject jsonObject = JSONObject.fromObject(content);
                token.setAccessToken(jsonObject.getString("access_token"));
                //存入redis
                redisTemplate.opsForValue().set(APPID + "_" + APPSECRET,token.getAccessToken(),7000, TimeUnit.SECONDS);

            }else {
                log.info("调用接口获取access_token失败");
            }
            log.info("获取token:{}",token.getAccessToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void pushMessage(List<UserDividendPushDTO> userDividendPushDTOS,String operatorType) throws Exception{
        try{
            if (userDividendPushDTOS != null && userDividendPushDTOS.size() > 0) {
                //推送消息数目
                int pushCount = 0;
                //判断当前时间是否是推送事件
                long now = DateUtil.dateTimeStamp(DateUtil.getDateYMDHM());
                log.info("当前时间戳：{}",now);
                for (UserDividendPushDTO userDividendPushDTO : userDividendPushDTOS) {
                    String sentDate = DateUtil.getChangeYMD(userDividendPushDTO.getSentDate());
                    //推送事件转换成时间戳
                    long pushTime = DateUtil.dateTimeStamp(sentDate + " " +  userDividendPushDTO.getSentTime());
                    log.info("推送时间戳：{}",pushTime);
                    if ( now == pushTime){
                        log.info("当前时间等于推送事件：推送");
                        List<UserFund> userFunds = userDividendPushDTO.getUserFunds();
                        if (userFunds != null && userFunds.size() > 0) {
                            for (UserFund userFund : userFunds) {
                                //组装并推送微信消息
                                JSONObject resultJson = push(userFund.getUserName(),userFund.getOpenId(),userDividendPushDTO.getNoticeName(),
                                        userDividendPushDTO.getMessageHref(),userDividendPushDTO.getMessageBegin(),userDividendPushDTO.getMessageEnd(),"0");
                                //access_token   失效  或过期 取最新token 并缓存  再重新补发一次
                                if ("40001".equals(resultJson.get("errcode").toString()) || "42001".equals(resultJson.get("errcode").toString())){
                                    log.info("access_token   失效  或过期 取最新token 并缓存  再重新补发一次");
                                    push(userFund.getUserName(),userFund.getOpenId(),userDividendPushDTO.getNoticeName(),
                                            userDividendPushDTO.getMessageHref(),userDividendPushDTO.getMessageBegin(),userDividendPushDTO.getMessageEnd(),"1");
                                }
                                pushCount++;
                            }
                            if ("1".equals(operatorType)){
                                //定时推消息  修改模板消息为已推送
                                //更新分红通知为已发送
                                FundDividendVO fundDividendVO = new FundDividendVO();
                                fundDividendVO.setSysId(userDividendPushDTO.getDividendId());
                                fundDividendVO.setSentStatus("2");
                                fundDividendVO.setUpdatedDate(new Date());
                                fundDividendVO.setUpdatedUser("system");
                                int row = fundDividendDao.updateFundDividend(fundDividendVO);
                                log.info("更新分红通知为已发送:{}", row);
                            }

                        }
                    }
                }
                log.info("微信推送消息数目：{}",pushCount);

            }
        }catch (Exception e) {
            log.error("推送消息异常",e);
        }

    }


    public JSONObject push(String userName,String openId,String noticeName,String messageHref,String essageBegin,
                           String getMessageEnd,String isRetry){
        JSONObject resultJson = null;
        try {
            //获取待发送消息
            Map<String, String> userMap = new HashMap<>();
            //客户信息
            userMap.put("userName", userName);
            userMap.put("openId", openId);
            //userMap.put("fundName", userFund.getFundName());
            userMap.put("tempplateId",dividendNotice);
            userMap.put("message", noticeName);
            userMap.put("url", messageHref);
            userMap.put("start",essageBegin);
            userMap.put("end", getMessageEnd);
            //待推送消息
            String sendStr = CommonWechatUtil.businessSend(userMap);
            JSONObject jsonObject = JSONObject.fromObject(sendStr);

            String token = Objects.toString(redisTemplate.opsForValue().get(APPID + "_" + APPSECRET));
            log.info("缓存中的token:{}",token);
            AccessToken at = null;
            String accessToken = null;
            //isRetry  是否重试操作  1 是 0 否  如果第一次 则直接从缓存中取access_token  重试则调用接口取最新
            if ("0".equals(isRetry)){
                if (token.length() > 0 && !token.equals("null")) {
                    log.info("=============TOKEN已存在===========");
                    accessToken = token;
                } else {
                    log.info("==========token 不存在需重新获取========");
                    // 调用接口获取access_token
                    at = getAccessToken();
                    accessToken = at.getAccessToken();
                    log.info("重新获取access_token：{}", accessToken);
                }
            }else if ("1".equals(isRetry)){
                log.info("==========重试需重新获取========");
                // 调用接口获取access_token
                at = getAccessToken();
                accessToken = at.getAccessToken();
                log.info("重试重新获取access_token：{}", accessToken);
            }
            System.setProperty("https.protocols","TLSv1,TLSv1.1,TLSv1.2");
            String result = HttpsUtil.sendPost(jsonObject, Constant.TEMPPLATE_PUSH_URL.replace("ACCESS_TOKEN", accessToken));
            log.info("推送公众号消息返回：{},用户名{}", result,userName);
            resultJson = JSONObject.fromObject(result);
            log.info("返回状态码：{}", resultJson.get("errcode"));
        }catch (Exception e){
            log.error("---推送公众号消息异常!---------",e);
        }
        return resultJson;

    }

}
