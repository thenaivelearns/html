package com.igw.market.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.igw.market.common.dao.AnnualBillDao;
import com.igw.market.common.domain.AnnualBillInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.service.AnnualBillService;
import com.igw.market.common.util.DateUtil;
import com.igw.market.common.util.JacksonUtil;
import com.igw.market.common.util.PageUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 年度账单
 * 
 * @author aiyongqiang
 *
 */
@RestController
@RequestMapping("annualBill")
@Slf4j
public class AnnualBillController {

	@Value("${wechat_code}")
	private String wechatCode;

	@Autowired
	private AnnualBillService annualBillService;

	@Autowired
	private AnnualBillDao annualBillDao;


	@RequestMapping("list")
	public ResultMessage searchList(@RequestBody String body) {
		Integer page = JacksonUtil.parseInteger(body, "page", 1);
		Integer size = JacksonUtil.parseInteger(body, "size", 10);
		// 查询条件
		Map<String, Object> map;
		try {
			map = JacksonUtil.parseObject(body, "query", Map.class);
		} catch (Exception e) {
			map = new HashMap<>();
		}
		map.put("wechatCode", wechatCode);
		PageUtil<WechatUserInfo> pageUtil = annualBillService.searchList(page, size, map);
		return ResultMessage.ok(pageUtil);
	}

	/*
	 * 批量同步年度账单
	 */
	@RequestMapping("synchro")
	public ResultMessage synchro(@RequestBody Map<String, String> map) throws InterruptedException {
		String year = map.get("year");
		if (StringUtils.isBlank(year)) {
			return ResultMessage.fail("年份不能为空");
		} else {
			// 获取当前年份
			String nowYear = DateUtil.getYYYY();
			try {
				if (year.compareTo(nowYear) >= 0) {
					return ResultMessage.fail("年份条件必须小于当前年份");
				}
			} catch (Exception e) {
				return ResultMessage.fail("日期格式异常");
			}
		}
		// 查询未同步年度账单的用户
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("year", year);
		pMap.put("wechatCode", wechatCode);
		pMap.put("isOn", "1");
		List<WechatUserInfo> tlist = annualBillDao.searchList(pMap);
		Set<String> cSet = new LinkedHashSet<>();
		for (WechatUserInfo wechatUserInfo : tlist) {
			String identityId = wechatUserInfo.getIdentityId();		
			if (StringUtils.isNotBlank(identityId)) {
				cSet.add(identityId);
			}
		}
		log.info("开始同步");
		if (null == cSet || cSet.size() < 1) {
			return ResultMessage.fail("信息已经全部同步");
		} else {
			int size = cSet.size();
			log.info("本次同步个数:" + size);
			final int nThreads = size > 20 ? 20 : size;
			List<Callable<String>> taskList = new ArrayList<>();
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			List<String> cList = new ArrayList<>();
			cList.addAll(cSet);
			int remaider = size % nThreads; // 计算出余数
			int number = size / nThreads; // 计算出商
			int offset = 0;// 偏移量
			for (int i = 0; i < nThreads; i++) {
				List<String> subList;
				if (remaider > 0) {
					subList = cList.subList(i * number + offset, (i + 1) * number + offset + 1);
					remaider--;
					offset++;
				} else {
					subList = cList.subList(i * number + offset, (i + 1) * number + offset);
				}
				Callable<String> task = new Callable<String>() {
					public String call() throws Exception {
						for (String identityId : subList) {
							log.info("正在同步身份证号信息:" + identityId);
							Map<String, Object> paramM = new HashMap<>();
							paramM.put("identityId", identityId);
							paramM.put("wechatCode", wechatCode);
							List<WechatUserInfo> tList = annualBillDao.searchList(paramM);
							AnnualBillInfo annualBillInfo = new AnnualBillInfo();
							annualBillInfo.setCustno(identityId);
							annualBillInfo.setTDate(year);
							if (null != tList && tList.size() > 0) {
								try {
									Map<String, Object> returnMap = annualBillService.getCustNo(identityId,
											tList.get(0).getIdentitytype());
									if (null == returnMap || StringUtils.isBlank(String.valueOf(returnMap.get("CUSTNO") ))) {
										annualBillInfo.setCstate("N");
										annualBillInfo.setMsgdesc("身份证:" + identityId + "客户号不存在");
									} else {
										String custNo = String.valueOf(returnMap.get("CUSTNO"));
										String firstDay = year+"0101";
										String lastDay = year + "1231";
										ResultMessage resultMessage = annualBillService.getMyAnnualMonthBill(custNo, firstDay, lastDay);
										if (resultMessage.isOk()) {
											annualBillInfo.setCstate("Y");
											annualBillInfo.setBill(JSON.toJSONString(resultMessage.getData()));
										} else {
											log.error(custNo + "账单生成失败:" + resultMessage.getMsgDesc());
											annualBillInfo.setCstate("N");
											annualBillInfo.setMsgdesc(resultMessage.getMsgDesc());
										}
									}
								} catch (Exception e) {
									log.error(identityId + "账单生成异常", e);
									annualBillInfo.setCstate("N");
									annualBillInfo.setMsgdesc("获取账单异常:" + e.getMessage());
								}
							} else {
								annualBillInfo.setCstate("N");
								annualBillInfo.setMsgdesc(identityId + "用户不存在");
							}
							annualBillInfo.setCreatedUser("system");
							annualBillDao.addBill(annualBillInfo);
						}
						return "ok";
					}
				};
				taskList.add(task);
			}
			executorService.invokeAll(taskList);
			executorService.shutdown();

			return ResultMessage.ok("同步完成:" + cSet.size());
		}
	}
	
	
	/*
	 * 批量同步月度年度账单
	 */
	@RequestMapping("synchroMonth")
	public ResultMessage synchroMonth(@RequestBody Map<String, String> map) throws InterruptedException {
		String monthTime = map.get("monthTime");
		if (StringUtils.isBlank(monthTime)) {
			return ResultMessage.fail("日期不能为空");
		} else {
			// 获取当前月份
			String nowMonth = DateUtil.getYYYYMM();
			try {
				if (monthTime.compareTo(nowMonth) >= 0) {
					return ResultMessage.fail("月份条件必须小于当前月份");
				}
			} catch (Exception e) {
				return ResultMessage.fail("日期格式异常");
			}
		}
		// 查询未同步年度账单的用户
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("year", monthTime);
		pMap.put("wechatCode", wechatCode);
		pMap.put("isOn", "1");
		List<WechatUserInfo> tlist = annualBillDao.searchList(pMap);
		Set<String> cSet = new LinkedHashSet<>();
		for (WechatUserInfo wechatUserInfo : tlist) {
			String identityId = wechatUserInfo.getIdentityId();		
			if (StringUtils.isNotBlank(identityId)) {
				cSet.add(identityId);
			}
		}
		log.info("开始同步");
		if (null == cSet || cSet.size() < 1) {
			return ResultMessage.fail("信息已经全部同步");
		} else {
			int size = cSet.size();
			log.info("本次同步个数:" + size);
			final int nThreads = size > 20 ? 20 : size;
			List<Callable<String>> taskList = new ArrayList<>();
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			List<String> cList = new ArrayList<>();
			cList.addAll(cSet);
			int remaider = size % nThreads; // 计算出余数
			int number = size / nThreads; // 计算出商
			int offset = 0;// 偏移量
			for (int i = 0; i < nThreads; i++) {
				List<String> subList;
				if (remaider > 0) {
					subList = cList.subList(i * number + offset, (i + 1) * number + offset + 1);
					remaider--;
					offset++;
				} else {
					subList = cList.subList(i * number + offset, (i + 1) * number + offset);
				}
				Callable<String> task = new Callable<String>() {
					public String call() throws Exception {
						for (String identityId : subList) {
							log.info("正在同步身份证号信息:" + identityId);
							Map<String, Object> paramM = new HashMap<>();
							paramM.put("identityId", identityId);
							paramM.put("wechatCode", wechatCode);
							List<WechatUserInfo> tList = annualBillDao.searchList(paramM);
							AnnualBillInfo annualBillInfo = new AnnualBillInfo();
							annualBillInfo.setCustno(identityId);
							annualBillInfo.setTDate(monthTime);
							if (null != tList && tList.size() > 0) {
								try {
									Map<String, Object> returnMap = annualBillService.getCustNo(identityId,
											tList.get(0).getIdentitytype());
									if (null == returnMap || StringUtils.isBlank(String.valueOf(returnMap.get("CUSTNO") ))) {
										annualBillInfo.setCstate("N");
										annualBillInfo.setMsgdesc("身份证:" + identityId + "客户号不存在");
									} else {
										String custNo = String.valueOf(returnMap.get("CUSTNO"));
										// 获取当月最后一天
										String firstDay = monthTime+"01";
										String lastDay = DateUtil.getLastDayMonth(monthTime);
										ResultMessage resultMessage = annualBillService.getMyAnnualMonthBill(custNo, firstDay, lastDay);
										if (resultMessage.isOk()) {
											annualBillInfo.setCstate("Y");
											annualBillInfo.setBill(JSON.toJSONString(resultMessage.getData()));
										} else {
											log.error(custNo + "账单生成失败:" + resultMessage.getMsgDesc());
											annualBillInfo.setCstate("N");
											annualBillInfo.setMsgdesc(resultMessage.getMsgDesc());
										}
									}
								} catch (Exception e) {
									log.error(identityId + "账单生成异常", e);
									annualBillInfo.setCstate("N");
									annualBillInfo.setMsgdesc("获取账单异常:" + e.getMessage());
								}
							} else {
								annualBillInfo.setCstate("N");
								annualBillInfo.setMsgdesc(identityId + "用户不存在");
							}
							annualBillInfo.setCreatedUser("system");
							annualBillDao.addBill(annualBillInfo);
						}
						return "ok";
					}
				};
				taskList.add(task);
			}
			executorService.invokeAll(taskList);
			executorService.shutdown();

			return ResultMessage.ok("同步完成:" + cSet.size());
		}
	}
}