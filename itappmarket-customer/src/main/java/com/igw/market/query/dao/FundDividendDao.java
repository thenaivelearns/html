package com.igw.market.query.dao;

import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.FundDividendDTO;
import com.igw.market.query.domain.vo.FundDividendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FundDividendDao {
    /**
     * @param fundDividend
     * @return 添加分红信息
     */
    int insertFundDividend(FundDividend fundDividend);

    /**
     * 修改分红信息
     *
     * @param fundDividend
     * @return
     */
    int updateFundDividend(@Param("fundDividend") FundDividendVO fundDividend);

    /**
     * 删除分红信息
     *
     * @param
     * @return
     */
    int deleteFundDividend(@Param("ids") List<String> ids);

    /**
     * 查询分红信息
     *
     * @param
     * @return
     */
    List<FundDividendDTO> queryFundDividend(@Param("fundDividend") FundDividend fundDividend);


}
