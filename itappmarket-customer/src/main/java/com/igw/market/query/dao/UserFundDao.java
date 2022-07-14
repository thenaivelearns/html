package com.igw.market.query.dao;

import com.igw.base.dynamicdatasource.annotation.DBType;
import com.igw.market.datasource.HangSengDataSource;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.domain.dto.UserFund;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFundDao {

    @DBType(value= HangSengDataSource.class)
    List<UserFund> queryUserFund(UserPushVO userPushVO);

    /**
     *	 通过微信身份证查询持有产品编码
     * @param Ide
     * @return
     */
    @DBType(value= HangSengDataSource.class)
    List<String> userFundCodeByIde(UserFund userFund);
}
