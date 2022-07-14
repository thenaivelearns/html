package com.igw.market.query.provider;

import com.igw.market.query.domain.dto.FundInfoDTO;
import com.igw.market.query.domain.dto.IgwHttpEntity;
import com.igw.market.query.domain.vo.FundInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 调用基金服务
 */
@FeignClient(value = "ItApp-ProductInfo-Management")
public interface FundInfoProvider {
    /**
     * 调用基金服务,获取基金信息
     *
     * @param igwFundInfoVO
     * @return
     */
    @PostMapping(value = "/ItApp-ProductInfo-Management/info/get_fund_base_list/v1")
    IgwHttpEntity<List<FundInfoDTO>> getFundBaseList(@RequestBody IgwHttpEntity<FundInfoVO> igwFundInfoVO);

}
