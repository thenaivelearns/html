package com.igw.market.open;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.igw.market.common.dao.AnnualBillDao;
import com.igw.market.common.domain.AnnualBillInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.service.AnnualBillService;
import com.igw.market.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("open")
@Slf4j
public class HttpOpenController {
	
	@Value("${wechat_code}")
	private String wechatCode;
	
	@Autowired
	private AnnualBillService annualBillService;
	
	@Autowired
	private AnnualBillDao annualBillDao;
	
	@RequestMapping("test")
	public String test() {
		return JSON.toJSONString(ResultMessage.ok());
	}
	
	/**
	 * 月度账单
	 * @param req
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getMyBill")
	public ResultMessage getMyMonthBill(HttpServletRequest req) throws ParseException {
		String month = req.getParameter("month");
		String identityId = req.getParameter("identityId");
		String identityType = req.getParameter("identityType");
		if(StringUtils.isBlank(month)) {
			return ResultMessage.fail("月份/年份不能为空");
		} else {
			if(month.length() != 6 && month.length() != 4) {
				return ResultMessage.fail("日期格式异常");
			}
			// 获取当前月份
			String nowMonth = DateUtil.getYYYYMM();
			try {
				if (month.compareTo(nowMonth) >= 0) {
					return ResultMessage.fail("月份条件必须小于当前月份");
				}
			} catch (Exception e) {
				return ResultMessage.fail("日期格式异常");
			}	
		}	
		/**
		 * 判断客户号账单是否已生成
		 * 1. 有直接返回
		 * 2. 无生成年度账单
		 */
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("custno", identityId);
		paramMap.put("year", month);
		List<AnnualBillInfo> list = annualBillDao.getMyAnnualBill(paramMap);
		// 获取客户号和中文名
		Map<String, Object> returnMap = annualBillService.getCustNo(identityId, identityType);
		if(returnMap == null) {
			return ResultMessage.fail("未查询到账户信息");
		}
		String custNo = String.valueOf(returnMap.get("CUSTNO"));
		String userName = String.valueOf(returnMap.get("CUSTNAME"));
		if(null != list && list.size() > 0) {
			AnnualBillInfo annualBillInfo = list.get(0);
			Map<String, Object> obj = (Map<String, Object>) JSON.parse(annualBillInfo.getBill());
			if(!obj.isEmpty())
			   obj.put("userName", userName);
			return ResultMessage.ok(obj);
		} else {
			// 生成账单
			if(StringUtils.isBlank(custNo)) {
				return ResultMessage.fail("暂未开通账户");
			}
			// 生成账单
			AnnualBillInfo annualBillInfo = new AnnualBillInfo();
			annualBillInfo.setCustno(identityId);
			annualBillInfo.setTDate(month);
			ResultMessage resultMessage = new ResultMessage();
			try {
				String firstDay;
				String lastDay;
				if(month.length() == 4) {
					 firstDay = month+"0101";
					 lastDay = month + "1231";
				} else {
					 firstDay = month+"01";
					 lastDay = DateUtil.getLastDayMonth(month);
				}
				resultMessage = annualBillService.getMyAnnualMonthBill(custNo, firstDay, lastDay);
				Map<String, Object> obj = (Map<String, Object>) resultMessage.getData();
				if(!obj.isEmpty())
					   obj.put("userName", userName);
				if(resultMessage.isOk()) {
					annualBillInfo.setCstate("Y");
					annualBillInfo.setBill(JSON.toJSONString(resultMessage.getData()));
				} else {
					log.error(custNo + "账单生成失败:" + resultMessage.getMsgDesc());
					annualBillInfo.setCstate("N");
					annualBillInfo.setMsgdesc(resultMessage.getMsgDesc());
				}
			} catch (Exception e) {
				log.error(custNo + "账单生成异常",e);
				resultMessage.setMsgDesc("获取账单失败,请联系管理员！");
				annualBillInfo.setCstate("N");
				annualBillInfo.setMsgdesc("获取账单异常:"+e.getMessage());
			}
			annualBillInfo.setCreatedUser("system");
			annualBillDao.addBill(annualBillInfo);
			return resultMessage;
		}		
	}

	/**
	 * 个人年度账单
	 * @return
	 */
	@RequestMapping("getMyYearBill")
	public ResultMessage getMyBill(HttpServletRequest req) throws ParseException {
		String identityId = req.getParameter("identityId");
		String identityType = req.getParameter("identityType");
		String year = req.getParameter("year");
		if(StringUtils.isBlank(year)) {
			return ResultMessage.fail("年份不能为空");
		} else {
			if(year.length() != 4) {
				return ResultMessage.fail("日期格式异常");
			}
			// 获取当前年份
			String nowYear = DateUtil.getYYYY();	
			try {
				if(year.compareTo(nowYear) >= 0) {
					return ResultMessage.fail("年份条件必须小于当前年份");
				}
			} catch (Exception e) {
				return ResultMessage.fail("日期格式异常");
			}		
		}	
		/**
		 * 判断客户号账单是否已生成
		 * 1. 有直接返回
		 * 2. 无生成年度账单
		 */
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("custno", identityId);
		paramMap.put("year", year);
		List<AnnualBillInfo> list = annualBillDao.getMyAnnualBill(paramMap);
		// 获取客户号和中文名
		Map<String, Object> returnMap = annualBillService.getCustNo(identityId, identityType);
		if(returnMap == null) {
			return ResultMessage.fail("未查询到账户信息");
		}
		String custNo = String.valueOf(returnMap.get("CUSTNO"));
		String userName = String.valueOf(returnMap.get("CUSTNAME"));
		if(null != list && list.size() > 0) {
			AnnualBillInfo annualBillInfo = list.get(0);
			Map<String, Object> obj = (Map<String, Object>) JSON.parse(annualBillInfo.getBill());
			if(!obj.isEmpty())
			   obj.put("userName", userName);
			return ResultMessage.ok(obj);
		} else {
			// 生成账单
			if(StringUtils.isBlank(custNo)) {
				return ResultMessage.fail("暂未开通账户");
			}
			AnnualBillInfo annualBillInfo = new AnnualBillInfo();
			annualBillInfo.setCustno(identityId);
			annualBillInfo.setTDate(year);
			ResultMessage resultMessage = new ResultMessage();
			try {
				resultMessage = annualBillService.getMyAnnualBill(custNo, year);
				Map<String, Object> obj = (Map<String, Object>) resultMessage.getData();
				if(!obj.isEmpty())
					   obj.put("userName", userName);
				if(resultMessage.isOk()) {
					annualBillInfo.setCstate("Y");
					annualBillInfo.setBill(JSON.toJSONString(resultMessage.getData()));
				} else {
					log.error(custNo + "账单生成失败:" + resultMessage.getMsgDesc());
					annualBillInfo.setCstate("N");
					annualBillInfo.setMsgdesc(resultMessage.getMsgDesc());
				}
			} catch (Exception e) {
				log.error(custNo + "账单生成异常",e);
				resultMessage.setMsgDesc("获取账单失败,请联系管理员！");
				annualBillInfo.setCstate("N");
				annualBillInfo.setMsgdesc("获取账单异常:"+e.getMessage());
			}
			annualBillInfo.setCreatedUser("system");
			annualBillDao.addBill(annualBillInfo);
			return resultMessage;
		}
	}
	
}
