package com.igw.market.common.api;

import com.igw.market.common.domain.WorkDateInfo;
import com.igw.market.common.domain.WorkDateInfoRequest;
import com.igw.market.query.domain.dto.IgwHttpEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description 交易日相关校验
 * @Author wuyanfei
 *
 */
@FeignClient(value = "ItApp-Calendar")
public interface CalendarService {


	@PostMapping({"/ItApp-Calendar/calendar/is_work_date/v1"})
	IgwHttpEntity<WorkDateInfo> getWorkDayInfo(@RequestBody IgwHttpEntity<WorkDateInfoRequest> var1);



	@PostMapping({"/ItApp-Calendar/calendar/calc_work_date/v1"})
	IgwHttpEntity<WorkDateInfo> workDateCalculation(@RequestBody IgwHttpEntity<WorkDateInfoRequest> var1);

}
