package com.igw.market.query.service.impl;

import com.igw.market.common.domain.BasePageInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.handler.CalendarHandler;
import com.igw.market.common.util.DateUtil;
import com.igw.market.common.util.PageUtil;
import com.igw.market.push.enums.TestUserEnum;
import com.igw.market.push.service.UtilService;
import com.igw.market.push.vo.UserDividendPushVO;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.dao.UserFundDao;
import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.*;
import com.igw.market.query.domain.vo.FundInfoVO;
import com.igw.market.query.provider.FundInfoProvider;
import com.igw.market.query.service.FundDividendService;
import com.igw.market.query.service.UserFundService;
import com.igw.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserFundServiceImpl implements UserFundService {

    @Value("${weixin.appid}")
    public  String APPID;

    @Value("${weixin.appsecret}")
    public String APPSECRET ;

    @Value("${dividend.notice.template}")
    public String dividendNotice;

    @Value("${wechat_code}")
    public String wechatCode;

    @Value("${user.openid}")
    public String userOpenIds;

    @Autowired
    private UserFundDao userFundDao;
    @Autowired
    private FundDividendService fundDividendService;

    @Autowired
    private FundInfoProvider fundInfoProvider;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UtilService utilService;

    @Autowired
    private CalendarHandler calendarHandler;

    @Override
    public List<UserFund> queryAllFund(UserPushVO userPushVO) {
        return userFundDao.queryUserFund(userPushVO);
    }

    @Override
    public PageUtil userPushList(UserPushVO userPushVO) throws Exception {
        //??????????????????????????????
        FundDividend fundDividend = new FundDividend();
        //????????????
        fundDividend.setReviewStatus("2");
        fundDividend.setSentStatus("1");
        //????????? ?????? ??????
        List<UserFund> userFundList = new ArrayList<>();
        //????????????????????????
        List<FundDividendDTO> fundDividends = fundDividendService.queryFundDividend(fundDividend);

        List<UserDividendPushDTO> userDividendPushDTOS = new ArrayList<>();
        FundInfoVO fundInfoVO = new FundInfoVO();
        //??????????????????
        fundInfoVO.setStatus("10");
        //undInfoVO.setFundCodes(c.getFundCode());
        IgwHttpEntity<List<FundInfoDTO>> fundBaseList = fundInfoProvider.getFundBaseList(IgwHttpEntity.buildSuccessResponse(fundInfoVO));
        List<FundInfoDTO> infoDTOList = fundBaseList.getData().getBody();
        if (fundDividends != null && fundDividends.size() > 0){
            for (FundDividend dividend:fundDividends){
                //???????????????????????????-?????? ??????
                List<String> fundCodes = Arrays.asList(dividend.getFundCode().split(","));
                userPushVO.setFundCodes(fundCodes);
                //userPushVO.setFundCode(dividend.getFundCode());
                userPushVO.setFilterAmount(dividend.getFilterAmount());
                if (StringUtil.isEmpty(userPushVO.getWechatCode())){
                    userPushVO.setWechatCode(wechatCode);
                }
                String now = DateUtil.getYYYYMMDD();
                String beforeTradeDay = calendarHandler.getBeforOrAfterTradeDay(now,"-1","yyyyMMdd");
                log.info("?????????????????????{}",beforeTradeDay);
                String convertTime = DateUtil.getChangeYMD8(beforeTradeDay);
                log.info("????????????:{}",convertTime);
                userPushVO.setTDate(convertTime);
                List<UserFund> userFunds = userFundDao.queryUserFund(userPushVO);


                //userFundList.addAll(userFunds);
                if (userFunds != null && userFunds.size() > 0){
                    for (UserFund c:userFunds){
                        if (infoDTOList != null && infoDTOList.size() > 0){
                            for (FundInfoDTO m:infoDTOList){
                                if (c.getFundCode().equals(m.getFundCode())){
                                    c.setFundName(m.getFundFullName());
                                    continue;
                                }
                            }
                        }
                    }
                }

                Map<String, List<UserFund>> userGroupMap = userFunds.stream().collect(Collectors.groupingBy(UserFund::getOpenId));
                List<List<UserFund>> userList =  userGroupMap.values().stream().collect(Collectors.toList());

                if (userList != null && userList.size() > 0){
                    for (List<UserFund> userFundsList:userList){
                        if (userFundsList != null && userFundsList.size() > 0){
                            for (UserFund c :userFundsList){
                                UserDividendPushDTO dividendPushDTO = new UserDividendPushDTO();
                                BeanUtils.copyProperties(dividend,dividendPushDTO);
                                dividendPushDTO.setDividendId(dividend.getSysId());
                                dividendPushDTO.setUserFund(c);
                                userDividendPushDTOS.add(dividendPushDTO);
                            }
                        }
                    }
                }


            }
        }
        //userPushVO.setWechatCode(wechatCode);
        //List<UserDividendPushDTO> userDividendPushDTOS = createData(userPushVO,"1");
        BasePageInfo basePageInfo = BasePageInfo.getPageInfo(userDividendPushDTOS,userPushVO.getPageNumber(),userPushVO.getPageSize());
        return new PageUtil(basePageInfo.getTotal(), basePageInfo.getList());
    }



    @Override
    public ResultMessage userPushTest(UserDividendPushVO userDividendPushDTO) throws Exception {
        //??????????????????
        //??????????????????????????????
        FundDividend fundDividend = new FundDividend();
        fundDividend.setSysId(userDividendPushDTO.getDividendId());
        //????????? ?????? ??????
        //????????????????????????
        UserDividendPushDTO dividendPushDTO = new UserDividendPushDTO();
        List<FundDividendDTO> fundDividends = fundDividendService.queryFundDividend(fundDividend);
        FundDividendDTO fundDividendDTO = fundDividends.get(0);
        JSONObject resultJson = utilService.push(userDividendPushDTO.getUserName(),userDividendPushDTO.getOpenId(),fundDividendDTO.getNoticeName(),
                fundDividendDTO.getMessageHref(),fundDividendDTO.getMessageBegin(),fundDividendDTO.getMessageEnd(),"0");
        if ("40001".equals(resultJson.get("errcode").toString()) || "42001".equals(resultJson.get("errcode").toString())){
            log.info("access_token   ??????  ????????? ?????????token ?????????  ?????????????????????");
            utilService.push(userDividendPushDTO.getUserName(),userDividendPushDTO.getOpenId(),userDividendPushDTO.getNoticeName(),
                    userDividendPushDTO.getMessageHref(),userDividendPushDTO.getMessageBegin(),userDividendPushDTO.getMessageEnd(),"1");
        }
        return ResultMessage.ok();
    }

    private List<UserDividendPushDTO> createData(UserPushVO userPushVO, String type) throws Exception {

        //??????????????????????????????
        FundDividend fundDividend = new FundDividend();
        //????????????
        fundDividend.setReviewStatus("2");
        //?????????
        fundDividend.setSentStatus("1");
        //????????? ?????? ??????
        List<UserDividendPushDTO> userDividendPushDTOS = new ArrayList<>();
        //????????????????????????
        List<FundDividendDTO> fundDividends = fundDividendService.queryFundDividend(fundDividend);
        if (fundDividends != null && fundDividends.size() > 0) {
            for (FundDividend dividend : fundDividends) {
               /* UserDividendPushDTO userDividendPushDTO = new UserDividendPushDTO();
                userDividendPushDTO.setDividendId(dividend.getSysId());
                userDividendPushDTO.setSentDate(dividend.getSentDate());
                userDividendPushDTO.setSentTime(dividend.getSentTime());
                userDividendPushDTO.setMessageEnd(dividend.getMessageEnd());
                userDividendPushDTO.setMessageHref(dividend.getMessageHref());
                userDividendPushDTO.setMessageBegin(dividend.getMessageBegin());
                userDividendPushDTO.setNoticeName(dividend.getNoticeName());*/
                //???????????????????????????-?????? ??????
                List<String> fundCodes = Arrays.asList(dividend.getFundCode().split(","));
                userPushVO.setFundCodes(fundCodes);
                userPushVO.setFilterAmount(dividend.getFilterAmount());
                //?????????????????? 1 ?????? 2 ??????
                //userPushVO.setWechatCode(wechatCode);
                String now = DateUtil.getYYYYMMDD();
                String beforeTradeDay = calendarHandler.getBeforOrAfterTradeDay(now, "-1", "yyyyMMdd");
                log.info("?????????????????????{}", beforeTradeDay);
                String convertTime = DateUtil.getChangeYMD8(beforeTradeDay);
                log.info("????????????:{}", convertTime);
                userPushVO.setTDate(convertTime);

                List<UserFund> userFunds = userFundDao.queryUserFund(userPushVO);
                if (userFunds != null && userFunds.size() > 0) {
                    if ("1".equals(type)) {
                        FundInfoVO fundInfoVO = new FundInfoVO();
                        //??????????????????
                        fundInfoVO.setStatus("10");
                        IgwHttpEntity<List<FundInfoDTO>> fundBaseList = fundInfoProvider.getFundBaseList(IgwHttpEntity.buildSuccessResponse(fundInfoVO));
                        List<FundInfoDTO> infoDTOList = fundBaseList.getData().getBody();
                        //??????????????????
                        for (UserFund c : userFunds) {
                            if (infoDTOList != null && infoDTOList.size() > 0) {
                                for (FundInfoDTO m : infoDTOList) {
                                    if (c.getFundCode().equals(m.getFundCode())) {
                                        c.setFundName(m.getFundFullName());
                                        continue;
                                    }
                                }
                            }
                        }

                        Map<String, List<UserFund>> userGroupMap = userFunds.stream().collect(Collectors.groupingBy(UserFund::getOpenId));
                        List<List<UserFund>> userList =  userGroupMap.values().stream().collect(Collectors.toList());
                        if (userList != null && userList.size() > 0){
                            userList.forEach(c -> {
                                UserDividendPushDTO dividendPushDTO = new UserDividendPushDTO();
                                BeanUtils.copyProperties(dividend,dividendPushDTO);
                                dividendPushDTO.setDividendId(dividend.getSysId());
                                dividendPushDTO.setUserFunds(c);
                                userDividendPushDTOS.add(dividendPushDTO);
                            });
                        }
                    }
                }
            }
        }
        return userDividendPushDTOS;
    }



    @Override
    public List<UserDividendPushDTO> getPushList(UserPushVO userPushVO) throws Exception {

        //??????????????????????????????
        FundDividend fundDividend = new FundDividend();
        //????????????
        fundDividend.setReviewStatus("2");
        //?????????
        fundDividend.setSentStatus("1");
        //????????? ?????? ??????
        List<UserDividendPushDTO> userDividendPushDTOS = new ArrayList<>();
        //????????????????????????
        List<FundDividendDTO> fundDividends = fundDividendService.queryFundDividend(fundDividend);
        if (fundDividends != null && fundDividends.size() > 0) {
            //UserPushVO userPushVO = new UserPushVO();
            for (FundDividend dividend : fundDividends) {
                UserDividendPushDTO userDividendPushDTO = new UserDividendPushDTO();
                userDividendPushDTO.setDividendId(dividend.getSysId());
                userDividendPushDTO.setSentDate(dividend.getSentDate());
                userDividendPushDTO.setSentTime(dividend.getSentTime());
                userDividendPushDTO.setMessageEnd(dividend.getMessageEnd());
                userDividendPushDTO.setMessageHref(dividend.getMessageHref());
                userDividendPushDTO.setMessageBegin(dividend.getMessageBegin());
                userDividendPushDTO.setNoticeName(dividend.getNoticeName());
                //???????????????????????????-?????? ??????
                List<String> fundCodes = Arrays.asList(dividend.getFundCode().split(","));
                userPushVO.setFundCodes(fundCodes);
                userPushVO.setFilterAmount(dividend.getFilterAmount());
                //?????????????????? 1 ?????? 2 ??????
                //userPushVO.setWechatCode(wechatCode);
                String now = DateUtil.getYYYYMMDD();
                String beforeTradeDay = calendarHandler.getBeforOrAfterTradeDay(now,"-1","yyyyMMdd");
                log.info("?????????????????????{}",beforeTradeDay);
                String convertTime = DateUtil.getChangeYMD8(beforeTradeDay);
                log.info("????????????:{}",convertTime);
                userPushVO.setTDate(convertTime);

                List<UserFund> userFunds = userFundDao.queryUserFund(userPushVO);
                if (userFunds != null && userFunds.size() > 0){
                    //????????????
                    List<UserFund> uniqueProductFundInfoList = userFunds.stream().collect(
                            Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(c -> c.getOpenId()))), ArrayList::new)
                    );
                    userDividendPushDTO.setUserFund(uniqueProductFundInfoList.get(0));
                    userDividendPushDTO.setUserFunds(uniqueProductFundInfoList);
                    userDividendPushDTOS.add(userDividendPushDTO);
                }

            }
        }
        return userDividendPushDTOS;
    }

    @Override
    public List<String> userFundCodeByIde(String identityNo, String identityType) {
    	UserFund userFund=new UserFund();
    	userFund.setIdentityNo(identityNo);
    	userFund.setIdentityType(identityType);
    	
        return userFundDao.userFundCodeByIde(userFund);
    }

    @Override
    public List<UserFund> getTestUser() throws Exception {

        List<String> openIds = Arrays.asList(userOpenIds.split(";"));
        /*UserPushVO userPushVO = new UserPushVO();
        userPushVO.setOpenIdList(openIds);
        List<UserDividendPushDTO> userDividendPushDTOS = getPushList(userPushVO);*/
        //utilService.pushMessage(userDividendPushDTOS);
        List<UserFund> userFunds = TestUserEnum.toList(openIds);
        return userFunds;
    }
}
