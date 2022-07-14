package com.igw.market.common.handler;

import com.igw.market.common.api.CalendarService;
import com.igw.market.common.domain.WorkDateInfo;
import com.igw.market.common.domain.WorkDateInfoRequest;
import com.igw.market.query.domain.dto.IgwHttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 交易日相关校验
 * @Author wuyanfei
 *
 */
@Component
public class CalendarHandler {

	@Autowired
	private CalendarService calendarService;
	
	/**
	 * 查询指定日期的前/后N个交易日
	 * @param date 指定日期 （格式为 yyyyMMdd）
	 * @param date expression 表达式（+N为指定日期后N个交易日，-N反之）
	 * @param date OutFormat 返回类型 (yyyyMMdd等)
	 * @return 
	 */
	public String getBeforOrAfterTradeDay(String date,String expression,String outFormat){
		WorkDateInfoRequest request = new WorkDateInfoRequest();
		request.setMarketCode("CN_EX");
		request.setQueryDate(date);
		request.setExpression(expression);
		request.setOutFormat(outFormat);
		IgwHttpEntity<WorkDateInfo> httpResult = calendarService.workDateCalculation(IgwHttpEntity.buildSuccessResponse(request));
		return httpResult.getData().getBody().getQueryDate();
	}
	
	/**
	 * 查询指定日期是否是交易日
	 * @param date 指定日期 （格式为 yyyyMMdd）
	 * @return 
	 */
	public boolean getIsTradeDayFalg(String date){
		WorkDateInfoRequest request = new WorkDateInfoRequest();
		request.setMarketCode("CN_EX");
		request.setQueryDate(date);
		IgwHttpEntity<WorkDateInfo> httpResult = calendarService.getWorkDayInfo(IgwHttpEntity.buildSuccessResponse(request));
		return "Y".equals(httpResult.getData().getBody().getWorkFlag());
	}



}
