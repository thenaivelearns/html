package com.igw.market.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igw.market.common.dao.FundInfoDao;
import com.igw.market.common.domain.FundInfo;
import com.igw.market.common.service.FundInfoService;

@Service
public class FundInfoServiceImpl implements FundInfoService {

	@Autowired
    private FundInfoDao fundInfoDao;
	
	@Override
    public FundInfo getFundInfoByFileName(String fileName) {
        List<FundInfo> fundList = fundInfoDao.getFundInfo();
        for (FundInfo fund : fundList) {
            if (fileName.indexOf(fund.getKeyword()) > -1){
            	return fund;
            }
        }
        FundInfo fundInfo =new FundInfo();
        return fundInfo;
    }

	@Override
	public List<FundInfo> getFundInfoList() {
		List<FundInfo> fundList = fundInfoDao.getFundInfo();
		return fundList;
	}

}
