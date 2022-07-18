package com.igw.market.common.service;

import com.igw.market.common.domain.AlmanacInfo;
import com.igw.market.common.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @author zhaotd
 * @date 2021/9/24
 * 投资黄历
 */
public interface AlmanacService {

    /**
     * 	查询日历信息
     * @param queryMap
     * @return
     */
    List<AlmanacInfo> getAlmanac(Map<String, Object> queryMap);
    
    /**
     * 	查询日历信息 不分组
     * @param queryMap
     * @return
     */
    List<AlmanacInfo> getAlmanacList(Map<String, Object> queryMap);

    /**
     * 新增日历信息
     * @param info
     * @return
     */
    int addAlmanac(AlmanacInfo info);

    /**
     * 修改日历信息
     * @param info
     * @return
     */
    int editAlmanac(AlmanacInfo info);

    /**
     * 删除日历信息
     * @param info
     * @return
     */
    int deleteAlmanac(AlmanacInfo info);

    /**
     * 分页查询日历信息
     * @param map
     * @return
     */
    PageUtil<AlmanacInfo> searchAlmanac(Map<String, Object> map);
}
