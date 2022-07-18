package com.igw.market.query.service;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.PageUtil;
import com.igw.market.push.vo.DividendNoticeVO;
import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.FundDividendDTO;
import com.igw.market.query.domain.vo.FundDividendVO;

import java.util.List;

public interface FundDividendService {
    /**
     * @param fundDividendVO
     * @return 添加分红信息
     */
    int insertFundDividend(FundDividendVO fundDividendVO);

    /**
     * 修改分红信息
     *
     * @param fundDividend
     * @return
     */
    int updateFundDividend(FundDividendVO fundDividend);

    ResultMessage saveFundDividend(String userName ,FundDividendVO fundDividendVO);

    /**
     * 删除分红信息
     *
     * @param
     * @return
     */
    int deleteFundDividend(List<String> ids);

    /**
     * 查询分红信息
     *
     * @param
     * @return
     */
    List<FundDividendDTO> queryFundDividend(FundDividend fundDividend);

    /**
     * @Author os-zhubg
     * @Description 分页查询分红信息
     * @Date 2021/8/18 9:17
     * @Param [userPushVO]
     * @return com.igw.market.common.util.PageUtil
     **/
    PageUtil searchFundDividend(String userName, String systemId ,DividendNoticeVO dividendNoticeVO);

}
