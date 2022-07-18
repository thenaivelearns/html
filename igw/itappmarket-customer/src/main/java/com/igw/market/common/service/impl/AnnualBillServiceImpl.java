package com.igw.market.common.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igw.base.dynamicdatasource.annotation.DBType;
import com.igw.market.common.dao.AnnualBillDao;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.dto.MyOperationDto;
import com.igw.market.common.dto.UserFundAsset;
import com.igw.market.common.service.AnnualBillService;
import com.igw.market.common.util.DataUtils;
import com.igw.market.common.util.DateUtil;
import com.igw.market.common.util.PageUtil;
import com.igw.market.datasource.HangSengDataSource;


@Service
public class AnnualBillServiceImpl implements AnnualBillService{
	
	@Autowired
	private AnnualBillDao annualBillDao;

	public PageUtil<WechatUserInfo> searchList(Integer page, Integer size, Map<String, Object> map) {
		PageHelper.startPage(page, size);
		List<WechatUserInfo> list = annualBillDao.searchList(map);
		/*
		 * // 敏感数据处理 for (WechatUserInfo wechatUserInfo : list) { // 手机号处理
		 * wechatUserInfo.setUserMobile(DataUtils.commonDisplay(wechatUserInfo.
		 * getUserMobile())); // 邮箱处理
		 * wechatUserInfo.setUserEmail(DataUtils.commonDisplay(wechatUserInfo.
		 * getUserEmail())); // 身份证
		 * //wechatUserInfo.setIdentityId(DataUtils.commonDisplay(wechatUserInfo.
		 * getIdentityId())); // 基金账号 String fundAcco = wechatUserInfo.getFundAcco();
		 * if(StringUtils.isNotBlank(fundAcco)) { String[] strs = fundAcco.split(",");
		 * List<String> list2 = new ArrayList<>(); for (String str : strs) {
		 * list2.add(DataUtils.commonDisplay(str)); } String join =
		 * StringUtils.join(list2.toArray(), ","); wechatUserInfo.setFundAcco(join); }
		 * 
		 * }
		 */
	    PageInfo<WechatUserInfo> pageInfo = PageInfo.of(list);
		return new PageUtil(pageInfo.getTotal(), list);
	}

	@Override
	public WechatUserInfo getWechatUserDetail(String pkSerial) {
		return annualBillDao.getWechatUserDetail(pkSerial);
	}
	
	public MyOperationDto getOperatioDetailByAcco(String custNo, String year)  {
		String startDate =  year + "-01-01";
		String endDate = "";
		try {
			endDate = DateUtil.getYearYMDN(startDate, 1);
		} catch (ParseException e) {
			endDate = (Integer.parseInt(year) + 1) + "-01-01";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("cCustno", custNo);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		// 购买的基金
		List<String> fundCodes = annualBillDao.getOperationFundCodes(map);
		int fundCouns = 0;
		if(null != fundCodes)
			fundCouns = fundCodes.size();
		// 获取基金账户操作的次数
		int operationCount = annualBillDao.getOperationCounts(map);
		// 现金宝装换次数
		map.put("nowjinbao", "nowjinbao");
		// 账户类型是否包含直销
		Boolean boo = false;
		String property = annualBillDao.getProperty(custNo);
		if(StringUtils.isNotBlank(property) && property.length() >= 5) {
			char charAt = property.charAt(4);
			if(charAt != 'N') {
				boo = true;
			}
		}
		int nowJinbaoCounts = annualBillDao.getOperationCounts(map);
	    MyOperationDto myOperationDto = new MyOperationDto();
	    myOperationDto.setFundCodes(fundCouns);
	    myOperationDto.setOperationCounts(operationCount);
	    myOperationDto.setNowJinbaoCounts(nowJinbaoCounts);
	    myOperationDto.setIsDirectSelling(boo);
		return myOperationDto;
	}

	@Override
	@DBType(value=HangSengDataSource.class)
	public ResultMessage getMyAnnualBill(String custNo, String year)  {
		// 通过客户号获取基金账户
		List<String> fundAccoList = annualBillDao.getFundAccoByCustNo(custNo);
		if(null == fundAccoList || fundAccoList.size() == 0)
			return ResultMessage.fail("客户号"+custNo+"基金账号不存在");
		// 我的操作信息
		MyOperationDto myOperationDto = this.getOperatioDetailByAcco(custNo, year);
		//  年末资产 
        Map<String, Object> assmMap = new HashMap<>(); 
        assmMap.put("custNo", custNo);
        assmMap.put("date", year+"1231");
		String assets = annualBillDao.getPersonalAssets(assmMap);
		// 年度收益
		BigDecimal sumIncome = new BigDecimal("0");
		// 基金收益及收益率
		List<Map<String, Object>> fundProfitList = new ArrayList<>();
		// 基金收益和收益率
		Map<String, Object> detailinfoxMap = new HashMap<>();
		detailinfoxMap.put("startDate",  DateUtil.toDate(year+"0101"));
		detailinfoxMap.put("endDate",  DateUtil.toDate(year+"1231"));
		detailinfoxMap.put("custno", custNo);
		detailinfoxMap.put("resultMap", null);
		// 账户期间收益
		annualBillDao.fcCustiiDetailinfox(detailinfoxMap);
		Object fcCustiifox = detailinfoxMap.get("resultMap");
		if(fcCustiifox != null) {
			List<Map<String, Object>> fcList = (List<Map<String, Object>>) fcCustiifox;
			if(null != fcList && fcList.size() > 0) {
				for (Map<String, Object> map : fcList) {
					// 期间收益
					BigDecimal sessionIncome = (BigDecimal) map.get("SESSION_INCOME");
					// 基金代码
					String c_fundcode = (String) map.get("C_FUNDCODE");
					// 基金账号
					String maxfundacco = (String) map.get("MAXFUNDACCO");
					// 期初资产
					BigDecimal bigenAsset = (BigDecimal) map.get("BEGIN_ASSET");
					// 期末资产
					BigDecimal endAsset = (BigDecimal) map.get("END_ASSET");
					
					if(sessionIncome.compareTo(BigDecimal.ZERO) != 0 
							&& bigenAsset.compareTo(BigDecimal.ZERO) != 0 
							&& endAsset.compareTo(BigDecimal.ZERO) != 0 ) {
						sumIncome = sumIncome.add(sessionIncome);
						// 查询基金收益率
						Map<String, Object> singleiiyinfoMap = new HashMap<>();
						singleiiyinfoMap.put("startDate", DateUtil.toDate(year+"0101"));
						singleiiyinfoMap.put("endDate",  DateUtil.toDate(year+"1231"));
						singleiiyinfoMap.put("fundAcco",  maxfundacco);
						singleiiyinfoMap.put("fundCode",  c_fundcode);
						singleiiyinfoMap.put("resultMap",  null);
						annualBillDao.getSingleiiyinfo(singleiiyinfoMap);
						Object obj = singleiiyinfoMap.get("resultMap");
						if(null != obj) {
							List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
							if(list != null && list.size() > 0) {
								Map<String, Object> map2 = list.get(0);
								// 期间收益率
								BigDecimal pincmR = (BigDecimal) map2.get("PINCM_R");
								Boolean boo = true;
								for (Map<String, Object> map3 : fundProfitList) {
									if(c_fundcode.equals(map3.get("fundcode"))) {
										// 合并收益率
										BigDecimal o1 = (BigDecimal) map3.get("sessionIncome");
										BigDecimal r1 = (BigDecimal) map3.get("pincmR");
										map3.put("sessionIncome", o1.add(sessionIncome));
										map3.put("pincmR", o1.add(sessionIncome).divide(o1.divide(r1, 6, BigDecimal.ROUND_UP).add(sessionIncome.divide(pincmR, 6, BigDecimal.ROUND_UP)), 6, BigDecimal.ROUND_UP));
										boo = false;
										break;
									}
								}
								if(boo) {
									Map<String, Object> profitMap = new HashMap<>();
									profitMap.put("sessionIncome", sessionIncome);
								//	profitMap.put("maxfundacco", maxfundacco);
									profitMap.put("fundcode", c_fundcode);
									profitMap.put("pincmR", pincmR);
									fundProfitList.add(profitMap);
								} 
								
							}
						}
					}
				}
			}
		}
		// 排序
		if(null != fundProfitList) {
			// 排序
			Collections.sort(fundProfitList , new Comparator<Map<String, Object>>() {
		        @Override
		        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		            BigDecimal b1 = (BigDecimal) o1.get("sessionIncome");
		            BigDecimal b2 = (BigDecimal)  o2.get("sessionIncome");
		            return b2.compareTo(b1);
		        }
		    });
		}
		for (Map<String, Object> map2 : fundProfitList) {
			map2.put("sessionIncome", DataUtils.toBigDecimal(map2.get("sessionIncome")));
			map2.put("pincmR", DataUtils.toBigDecimal(map2.get("pincmR")) + "%");
			String fundcode = (String) map2.get("fundcode");
			String fundName = annualBillDao.getFundNameByCode(fundcode);
			if(StringUtils.isNotBlank(fundName)) {
				fundName = fundName.replaceAll("景顺长城", "").replaceAll("证券投资基金", "");
			}
			map2.put("fundName", fundName);
		}
		// 持有基金,收益
		List<Map<String, Object>> fundList = annualBillDao.getHoldFundDetail(assmMap);
		List<Map<String, Object>> typeList = getTypeList(fundList, assets);
		Map<String, Object> hashMap = new HashMap<>();
		if(StringUtils.isBlank(assets)) {
			assets = "0";
		}
		hashMap.put("assets", DataUtils.toBigDecimal(Double.parseDouble(assets)));
		hashMap.put("profit", DataUtils.toBigDecimal(sumIncome));
		hashMap.put("holdingFund",typeList);
		hashMap.put("myOperationDto", myOperationDto);
		hashMap.put("holdingFundDetail", fundList);
		// 排序
		hashMap.put("fundProfitDetail", fundProfitList);
		return ResultMessage.ok(hashMap); 
	}
	
	private BigDecimal assetsort(List<Map<String, Object>> assetDetailsList) {
		BigDecimal endFincmTotal = new BigDecimal("0");
		if(null != assetDetailsList) {
			Collections.sort(assetDetailsList , new Comparator<Map<String, Object>>() {
		        @Override
		        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		            BigDecimal b1 = (BigDecimal) o1.get("TOTALASSETS");
		            BigDecimal b2 = (BigDecimal)  o2.get("TOTALASSETS");
		            return b2.compareTo(b1);
		        }
		    });
			for(Map<String, Object> map : assetDetailsList) {
				String fundName = (String) map.get("FUNDNAME");
				if(StringUtils.isNotBlank(fundName)) {
					fundName = fundName.replaceAll("景顺长城", "").replaceAll("证券投资基金", "");
					map.put("FUNDNAME", fundName);
				}
				endFincmTotal = endFincmTotal.add((BigDecimal)map.get("TOTALASSETS"));
				map.put("TOTALASSETS", DataUtils.toBigDecimal(Double.parseDouble(map.get("TOTALASSETS") + "")));
				map.put("NETVALUE", DataUtils.toBigDecimal2(map.get("NETVALUE")));
				map.put("LASTSHARES", DataUtils.toBigDecimal(map.get("LASTSHARES")));
			}
		}
		return endFincmTotal;
	}
	

	public List<Map<String, Object>> getTypeList(List<Map<String, Object>> fundList, String assets) {
		if(null != fundList) {
			// 排序
			Collections.sort(fundList , new Comparator<Map<String, Object>>() {
		        @Override
		        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		            BigDecimal b1 = (BigDecimal) o1.get("ASSETS");
		            BigDecimal b2 = (BigDecimal)  o2.get("ASSETS");
		            return b2.compareTo(b1);
		        }
		    });
		}
		// 基金类型
		BigDecimal[] arr = {new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
		String[] strs = {"股票型","指数型","债券型","货币型","混合型","QDII","FOF"};
		for (Map<String, Object> map : fundList) {
			String fundType = (String) map.get("FUNDTYPE");
			String fundName = (String) map.get("FUNDNAME");
			if(StringUtils.isNotBlank(fundName)) {
				fundName = fundName.replaceAll("景顺长城", "").replaceAll("证券投资基金", "");
				map.put("FUNDNAME", fundName);
			}
			for(int i = 1; i <= 7; i++) {
				if(String.valueOf(i).equals(fundType)) {
					arr[i-1] = arr[i-1].add((BigDecimal)map.get("ASSETS")) ;
					BigDecimal bigDecimal = arr[i-1];
					break;
				}
			}
			map.put("ASSETS", DataUtils.toBigDecimal(Double.parseDouble(map.get("ASSETS") + "")));
			
		}
		List<Map<String, Object>> typeList = new ArrayList<>();
		for (int i = 0; i <= 6; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", strs[i]);
			map.put("value", arr[i]);
			typeList.add(map);
		}
		// 排序
		Collections.sort(typeList , new Comparator<Map<String, Object>>() {
	        @Override
	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	            BigDecimal b1 = (BigDecimal) o1.get("value");
	            BigDecimal b2 = (BigDecimal)  o2.get("value");
	            return b2.compareTo(b1);
	        }
	    });
		return typeList;
	}
	

	@Override
	@DBType(value=HangSengDataSource.class)
	public Map<String, Object> getCustNo(String identityId, String idType) {
		Map<String, Object> map = new HashMap<>();
		map.put("identityId", identityId);
		map.put("identitytype", idType);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if("8".equals(idType)) {
			// 通过基金账号获取客户号
			returnMap = annualBillDao.getCustNoByFundacco(identityId);
		} else {
			returnMap =  annualBillDao.getCustNo(map);
		}
		return returnMap;
	}

	@Override
	@DBType(value=HangSengDataSource.class)
	public ResultMessage getMyAnnualMonthBill(String custNo, String firstDay, String lastDay)  {
		// 通过客户号获取基金账户
		List<String> fundAccoList = annualBillDao.getFundAccoByCustNo(custNo);
		if(null == fundAccoList || fundAccoList.size() == 0)
			return ResultMessage.fail("客户号"+custNo+"基金账号不存在");
		//  月末资产 
        Map<String, Object> assmMap = new HashMap<>(); 
        assmMap.put("custNo", custNo);
        assmMap.put("date", lastDay);
		String assets = annualBillDao.getPersonalAssets(assmMap);
		// 月初资产
		Map<String, Object> fMap = new HashMap<>();
		fMap.put("custNo", custNo);
		String afterDay = "";
		try {
			afterDay = DateUtil.getDaysAfter(firstDay, -1);
		} catch (ParseException e) {
			afterDay = lastDay;
		}
		fMap.put("date", afterDay);
		String fiestAssets = annualBillDao.getPersonalAssets(fMap);
		// 月度收益
		BigDecimal sumIncome = new BigDecimal("0");
		// 持仓收益
		BigDecimal totalIncome = new BigDecimal("0");
			
		// 基金收益及收益率
		List<Map<String, Object>> fundProfitList = new ArrayList<>();
		// 基金收益和收益率
		Map<String, Object> detailinfoxMap = new HashMap<>();
		detailinfoxMap.put("startDate",   DateUtil.toDate(firstDay));
		detailinfoxMap.put("endDate",   DateUtil.toDate(lastDay));
		detailinfoxMap.put("custno", custNo);
		detailinfoxMap.put("resultMap", null);
		
		Map<String, Object> floatingprofitlMap = new HashMap<>();
		floatingprofitlMap.put("endDate",  lastDay);
		floatingprofitlMap.put("custno", custNo);
		// 查询持仓收益
		annualBillDao.getfloatingprofitlosscust(floatingprofitlMap);
		Object result = floatingprofitlMap.get("resultMap");
		if(result != null) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) result;
			if(null != list && list.size() > 0) {
				for (Map<String, Object> map : list) {	
                   // 浮动盈亏值
				   BigDecimal b = (BigDecimal) map.get("FLOATING_PROFIT_LOSS");
				   totalIncome = totalIncome.add(b);
				}
			}
		}

		// 账户期间收益
		annualBillDao.fcCustiiDetailinfox(detailinfoxMap);
		Object fcCustiifox = detailinfoxMap.get("resultMap");
		// 全程收益合计
		BigDecimal endFincmTotal = new BigDecimal("0");
		// 期间收益合计
		BigDecimal periodFincmTotal = new BigDecimal("0");
		if(fcCustiifox != null) {
			List<Map<String, Object>> fcList = (List<Map<String, Object>>) fcCustiifox;
			if(null != fcList && fcList.size() > 0) {
				for (Map<String, Object> map : fcList) {
					// 期间收益
					BigDecimal sessionIncome = (BigDecimal) map.get("SESSION_INCOME");
					// 期初资产
					BigDecimal bigenAsset = (BigDecimal) map.get("BEGIN_ASSET");
					// 期末资产
					BigDecimal endAsset = (BigDecimal) map.get("END_ASSET");
					// 全程收益
					BigDecimal endFincm = (BigDecimal)map.get("END_INCOME");
					// 基金代码
					String c_fundcode = (String) map.get("C_FUNDCODE");
					// 基金账号
					String maxfundacco = (String) map.get("MAXFUNDACCO");
					
					//期间收益为0 则过滤
					if(sessionIncome.compareTo(BigDecimal.ZERO) != 0
							|| bigenAsset.compareTo(BigDecimal.ZERO) != 0 
							|| endAsset.compareTo(BigDecimal.ZERO) != 0) {
						sumIncome = sumIncome.add(sessionIncome);
						// 查询基金收益率
						Map<String, Object> singleiiyinfoMap = new HashMap<>();
						singleiiyinfoMap.put("startDate", DateUtil.toDate(firstDay));
						singleiiyinfoMap.put("endDate",  DateUtil.toDate(lastDay));
						singleiiyinfoMap.put("fundAcco",  maxfundacco);
						singleiiyinfoMap.put("fundCode",  c_fundcode);
						singleiiyinfoMap.put("resultMap",  null);
						annualBillDao.getSingleiiyinfo(singleiiyinfoMap);
						Object obj = singleiiyinfoMap.get("resultMap");
						if(null != obj) {
							List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
							if(list != null && list.size() > 0) {
								Map<String, Object> map2 = list.get(0);
								// 期间收益率
								BigDecimal pincmR = (BigDecimal) map2.get("PINCM_R");
								Boolean boo = true;
								endFincmTotal = endFincmTotal.add(endFincm);
								periodFincmTotal = periodFincmTotal.add(sessionIncome);
								for (Map<String, Object> map3 : fundProfitList) {
									if(c_fundcode.equals(map3.get("fundcode"))) {
										// 合并收益率
										BigDecimal o1 = (BigDecimal) map3.get("sessionIncome");
										BigDecimal r1 = (BigDecimal) map3.get("pincmR");
										map3.put("sessionIncome", o1.add(sessionIncome));
										if(r1  != null && r1.compareTo(new BigDecimal("0")) != 0) {
											map3.put("pincmR", o1.add(sessionIncome).divide(o1.divide(r1, 6, BigDecimal.ROUND_UP).add(sessionIncome.divide(pincmR, 6, BigDecimal.ROUND_UP)), 6, BigDecimal.ROUND_UP));
										} else {
											map3.put("pincmR", new BigDecimal("0"));
										}
										// 全程收益
										BigDecimal o2 = (BigDecimal) map3.get("endFincm");
										map3.put("endFincm", o2.add(endFincm));
										boo = false;
										break;
									}
								}
								if(boo) {
									Map<String, Object> profitMap = new HashMap<>();
									profitMap.put("sessionIncome", sessionIncome);
								//	profitMap.put("maxfundacco", maxfundacco);
									profitMap.put("fundcode", c_fundcode);
									profitMap.put("pincmR", pincmR);
									profitMap.put("endFincm", endFincm);
									fundProfitList.add(profitMap);
								} 
								
							}
						}
					}
				}
			}
		}	
		
		// 资产明细
		List<Map<String, Object>> assetDetailsList = annualBillDao.getAssetDetailsList(assmMap);
		// 资产明细排序和基金名称处理 返回基金市值合计
		BigDecimal b = assetsort(assetDetailsList);
	
		// 排序
		if(null != fundProfitList) {
			// 排序
			Collections.sort(fundProfitList , new Comparator<Map<String, Object>>() {
		        @Override
		        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		            BigDecimal b1 = (BigDecimal) o1.get("sessionIncome");
		            BigDecimal b2 = (BigDecimal)  o2.get("sessionIncome");
		            return b2.compareTo(b1);
		        }
		    });
		}
		BigDecimal t1  = new BigDecimal("0");
		BigDecimal t2  = new BigDecimal("0");
		for (Map<String, Object> map2 : fundProfitList) {
			t1 = t1.add((BigDecimal) map2.get("sessionIncome"));
			BigDecimal b1 = (BigDecimal)map2.get("pincmR");
			if(b1 != null) {
				if(b1.compareTo(new BigDecimal("0")) > 0) {
					t2 = t2.add(((BigDecimal) map2.get("sessionIncome")).divide(b1, 6, BigDecimal.ROUND_UP));
				}
			} 
			map2.put("sessionIncome", DataUtils.toBigDecimal(map2.get("sessionIncome")));
			map2.put("pincmR", DataUtils.toBigDecimal(map2.get("pincmR")) + "%");
			String fundcode = (String) map2.get("fundcode");
			String fundName = annualBillDao.getFundNameByCode(fundcode);
			if(StringUtils.isNotBlank(fundName)) {
				fundName = fundName.replaceAll("景顺长城", "").replaceAll("证券投资基金", "");
			}
			map2.put("fundName", fundName);
		}
		// 持有基金,收益
		List<Map<String, Object>> fundList = annualBillDao.getHoldFundDetail(assmMap);
		List<Map<String, Object>> typeList = getTypeList(fundList, assets);
		Map<String, Object> hashMap = new HashMap<>();
		if(StringUtils.isBlank(assets)) {
			assets = "0";
		}
		
		
		// 月末资产
		if(assets == null) {
			assets = "0";
		}
		hashMap.put("assets", DataUtils.toBigDecimal(Double.parseDouble(assets)));
		if(fiestAssets == null) {
			fiestAssets = "0";
		}
		// 月初资产
		hashMap.put("fiestAssets", DataUtils.toBigDecimal(Double.parseDouble(fiestAssets)));
		
		Map<String, Object> detailmap = new HashMap<>();
		detailmap.put("startDate",  firstDay);
		detailmap.put("endDate",  lastDay);
		detailmap.put("custno", custNo);
		// 资产变动
		List<Map<String, Object>> list = annualBillDao.changesAssets(detailmap);
		// 转入
		BigDecimal assetsIn = new BigDecimal("0");
		// 转出
		BigDecimal assetsOut = new BigDecimal("0");
		if(null != list) {
			for(Map<String, Object> map: list) {
				BigDecimal balace = (BigDecimal) map.get("BALACE");
				if(balace == null) {
					balace = new BigDecimal(0);
				}
				String typeflage =  (String) map.get("TYPEFLAG");
				if("0".equals(typeflage)) {
					assetsIn = assetsIn.add(balace);
				} else if("1".equals(typeflage)) {
					assetsOut = assetsOut.add(balace);
				}
			}
		}
		hashMap.put("changeList", list);
		hashMap.put("assetsIn", DataUtils.toBigDecimal(assetsIn));
		hashMap.put("assetsOut", DataUtils.toBigDecimal(assetsOut));
		// 本月收益
	//	hashMap.put("profit", DataUtils.toBigDecimal(sumIncome));
//		assets  月末
//		fiestAssets 月初
//		assetsIn 转入
//		assetsOut 转出
		BigDecimal sumIncome2 = new BigDecimal(assets).add(assetsOut).subtract(new BigDecimal(fiestAssets)).subtract(assetsIn);
		hashMap.put("profit", DataUtils.toBigDecimal(sumIncome2));
		// 持仓收益
		hashMap.put("totalIncome", DataUtils.toBigDecimal(totalIncome));
		// 本月收益率
		String profitRate = "0";
		if(t2.compareTo(new BigDecimal("0")) != 0) {
			profitRate = DataUtils.toBigDecimal(t1.divide(t2, 3,  BigDecimal.ROUND_UP));
		}
		hashMap.put("profitRate", profitRate);
		// 持有基金类型
		hashMap.put("holdingFund",typeList);
		//hashMap.put("holdingFundDetail", fundList);
		hashMap.put("fundProfitDetail", fundProfitList);
		// 资产明细
		hashMap.put("assetDetailsList", assetDetailsList);
		// 全程收益合计
		hashMap.put("endFincmTotal", DataUtils.toBigDecimal(endFincmTotal));
		// 基金市值合计
		hashMap.put("marketTotal", DataUtils.toBigDecimal(b));
		// 期间收益合计
		hashMap.put("periodFincmTotal", DataUtils.toBigDecimal(periodFincmTotal));
		return ResultMessage.ok(hashMap); 
	}

}
