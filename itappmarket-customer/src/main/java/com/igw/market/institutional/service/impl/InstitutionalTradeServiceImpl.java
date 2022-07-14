package com.igw.market.institutional.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.igw.market.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igw.market.common.util.Email.MailSenderInfo;
import com.igw.market.common.util.Email.SimpleMailSender;
import com.igw.market.institutional.dao.InstitutionalTradeDao;
import com.igw.market.institutional.domain.InstitutionalTrade;
import com.igw.market.institutional.domain.InstitutionalTradeInfo;
import com.igw.market.institutional.service.InstitutionalTradeService;
import com.igw.market.institutional.util.FileServerUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class InstitutionalTradeServiceImpl implements InstitutionalTradeService {

	private static final Logger logger = Logger.getLogger(InstitutionalTradeServiceImpl.class);

	@Autowired
	InstitutionalTradeDao insDao;

	@Value("${template_path}")
	private String templatePath;

	@Value("${common_data}")
	private String commonData;

	@Value("${aggr_path}")
	private String aggrPath;

	@Value("${file_server}")
	private String fileServer;

	@Value(("${csdcl1_data}"))
	private String csdcl1Data;


	@Override
	public ResultMessage<Object> createTradeFile(String fileDate) {
		ResultMessage<Object> result =new ResultMessage<Object>();
		//获取当日要生成所有委托人信息
		List<InstitutionalTrade> insList = insDao.getInstitutionalTrade(new HashMap<>());
		logger.info("生成委托人数据量:"+insList.size());

		//获取月份日期
		String MMDD = fileDate.substring(4, fileDate.length());
		//文件类型可能是日期
		String MDD =  "0".equals(MMDD.charAt(0)+"")?MMDD.substring(1):MMDD;

		//设置交易所文件后缀， 10月：a01； 11月：b01 ；12月：c01
		if(MDD.length() == 4) {
			String MM = MDD.substring(0,2);
			String DD = MDD.substring(2,4);
			if("10".equals(MM)) {
				MDD = "a"+DD;
			}else if("11".equals(MM)) {
				MDD = "b"+DD;
			}else {
				MDD = "c"+DD;
			}
		}

		// 循环获得委托人需要文件
		for (InstitutionalTrade ins : insList) {
			logger.info(ins.getInstitutionalName()+"委托人");

			//文件导出路径
			String reportPath = ins.getReportPath().replace("YYYYMMDD", fileDate)+"/" ;
			Map<String, String> param=new HashMap<>();
			param.put("institutionalCode", ins.getPkSerial());
			List<InstitutionalTradeInfo> infoList = insDao.getInstitutionalTradeInfo(param);
			if(null != infoList && infoList.size() > 0) {
				logger.info("委托人需要文件数量"+infoList.size());
				List<String> fileList = new ArrayList<>();
				// 获取基础数据服务文件数据
				for (InstitutionalTradeInfo insi : infoList) {
					logger.info("文件名称"+insi.getFileName());

					if("2".equals(insi.getDataMode())) {
						// 文件服务器下载
						ResultMessage<Object> msg =FileServerUtil.getFileServerList(fileServer,
								insi.getFileName().replace("YYYYMMDD", fileDate),
								fileDate, aggrPath.replace("YYYYMMDD", fileDate));
						//下载成功
			            if("Y".equals(msg.getMsgCode())){
			            	//生成成交汇总
							TsvUtil.outTSV(aggrPath.replace("YYYYMMDD", fileDate)+insi.getFileName().replace("YYYYMMDD", fileDate), //文件源路径
									reportPath,   //文件新路径
									insi.getNewFileName().replace("YYYYMMDD", fileDate), //新文件名
									ins.getSzStockCode()); 				//过滤文件深圳股东代码
							String filePath = ins.getReportPath().replace("YYYYMMDD", fileDate) + File.separator + insi.getNewFileName().replace("YYYYMMDD", fileDate);
							//创建深证通.ok
                            FileUtil.copyFile(filePath ,filePath +".ok", true);
							// 加入文件列表
							fileList.add(filePath);
			            }
					}else{
						//填写接口入参
						JSONObject json =new JSONObject();
						if(StringUtils.isNotBlank(insi.getParam())) {
							//判断文件来源 1:深证，2:上海
							if("1".equals(insi.getFileSouce())) {
								json.put(insi.getParam(), ins.getSzStockCode());
							}else {
								json.put(insi.getParam(), ins.getShStockCode());
							}
						}
						json.put("fileDate", fileDate);
						try {

							logger.info("调用接口："+commonData+insi.getDataInterface()+",参数："+json);
							//调用接口获取文件数据
							JSONArray jsonArr = HttpsUtil.getInterfaceData(json, commonData+insi.getDataInterface());
							if (jsonArr.isEmpty()){
								logger.info("调用接口："+csdcl1Data+insi.getDataInterface()+",参数："+json);
								jsonArr = HttpsUtil.getInterfaceData(json, csdcl1Data+insi.getDataInterface());
							}
							logger.info("返回结果集:" +jsonArr);
							// 如果数据为空 且 不需要空文件 则直接下一次
							if(jsonArr.size() <= 0 && "1".equals(insi.getIsNullFile())) {
								continue;
							}
							// 生成文件
							String newFile = insi.getNewFileName().replace("MMDD", MMDD);
							newFile = newFile.replace("MDD", MDD);
							logger.info("文件生成路径:"+reportPath);
							boolean boo = false;
							if (newFile.toUpperCase().endsWith("TXT")){
								boo = TXTUtil.outputTXT(jsonArr, newFile,reportPath);
								logger.info("txt文件生成成功...");
							} else {
								boo = DBFUtil.outputDBF(jsonArr, newFile,
										templatePath+insi.getTemplateDBF(),reportPath);
								logger.info("dbf文件生成成功...");
							}
							// 生成文件成功
							if(Boolean.TRUE.equals(boo)) {
								String filePath = reportPath + newFile;
								//创建深证通.ok
	                            FileUtil.copyFile(filePath ,filePath +".ok", true);
	                            // 加入文件列表
								fileList.add(filePath);
							}
						} catch (Exception e) {
							logger.error("调用接口："+insi.getDataInterface()+" 生成文件失败。"+e.getMessage());
							result.setMsgCode("N");
							result.setMsgDesc("调用接口："+insi.getDataInterface()+" 生成文件失败。");
							return result;
						}
					}
				}
				// 配置了邮箱并且有附件则发送邮件
				if(fileList.size() > 0 && StringUtils.isNotBlank(ins.getEmail())) {
					MailSenderInfo mailInfo = new MailSenderInfo();
					// 是否打包发送
					if("0".equals(ins.getIsZip())) {
						String zipName;
						if(StringUtils.isBlank(ins.getReportName())) {
							zipName = ins .getInstitutionalName() + fileDate + "zip";
						} else {
							zipName = ins.getReportName().replace("YYYYMMDD", fileDate);
						}
						String zipPath = reportPath  + File.separator + zipName + ".zip";
						logger.info(ins.getInstitutionalName() + "生成打包文件路径" + zipPath);
						FileUtil.fileToZip(fileList, zipPath);
						mailInfo.setAttachFileNames(zipPath.split(";"));
					} else {
						mailInfo.setAttachFileNames(fileList.toArray(new String[fileList.size()]));
					}
					mailInfo.setToAddressList(ins.getEmail().split(";"));
					mailInfo.setSubject("【"+ ins.getInstitutionalName() + "】" + fileDate + "中登文件请查收!");
					ResultMessage res = SimpleMailSender.sendHtmlMailWithMore(mailInfo);
					if(res.isOk()) {
						logger.info(ins.getInstitutionalName() + "发送邮件成功");
					} else {
						logger.error(ins.getInstitutionalName() + "发送邮件失败:" + res.getMsgDesc());
					}
				}
			}


		}
		result.setMsgCode("Y");
		result.setMsgDesc("生成文件完成。");
		return result;
	}


	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String MDD = "1202";
		//设置交易所文件后缀， 10月：a01； 11月：b01 ；12月：c01
				if(MDD.length() == 4) {
					String MM = MDD.substring(0,2);
					String DD = MDD.substring(2,4);
					if("10".equals(MM)) {
						MDD = "a"+DD;
					}else if("11".equals(MM)) {
						MDD = "b"+DD;
					}else {
						MDD = "c"+DD;
					}
				}

		System.out.println(MDD);
	}


	@Override
	public PageUtil<InstitutionalTrade> searchList(Integer page, Integer size, Map<String, String> map) {
		PageHelper.startPage(page, size);
		List<InstitutionalTrade> insList = insDao.getInstitutionalTrade(map);
		PageInfo<InstitutionalTrade> pageInfo = PageInfo.of(insList);
		return new PageUtil(pageInfo.getTotal(), insList);
	}


	@Override
	public int addInstitutionalTrade(InstitutionalTrade info) {
		return insDao.add(info);
	}

}
