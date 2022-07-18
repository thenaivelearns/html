package com.igw.market.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igw.market.common.dao.AlmanacDao;
import com.igw.market.common.domain.AlmanacInfo;
import com.igw.market.common.service.AlmanacService;
import com.igw.market.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhaotd
 * @date 2021/9/24
 */
@Service
public class AlmanacServiceImpl implements AlmanacService {

    @Autowired
    private AlmanacDao almanacDao;

    @Override
    public List<AlmanacInfo> getAlmanac(Map<String, Object> queryMap) {
        return almanacDao.getAlmanac(queryMap);
    }

    @Override
    public int addAlmanac(AlmanacInfo info) {
        return almanacDao.addAlmanac(info);
    }

    @Override
    public int editAlmanac(AlmanacInfo info) {
        return almanacDao.editAlmanac(info);
    }

    @Override
    public int deleteAlmanac(AlmanacInfo info) {
        return almanacDao.deleteAlmanac(info);
    }

    @Override
    public PageUtil<AlmanacInfo> searchAlmanac(Map<String, Object> map) {
        Integer pageSize = (Integer) map.get("size");
        Integer pageNumber = (Integer) map.get("page");
        PageHelper.startPage(pageNumber,pageSize);
        List<AlmanacInfo> list = almanacDao.getAlmanac(map);
        PageInfo<AlmanacInfo> pageInfo = PageInfo.of(list);
        return new PageUtil<>(pageInfo.getTotal(),list);
    }

	@Override
	public List<AlmanacInfo> getAlmanacList(Map<String, Object> queryMap) {
		return almanacDao.getAlmanacList(queryMap);
	}
}
