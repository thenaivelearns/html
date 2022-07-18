package com.igw.market.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.FundInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.service.FundInfoService;

@RestController
@RequestMapping("/fundInfo")
public class FundInfoController {
	
	@Autowired
    private FundInfoService fundInfoService;
    
	 /**
	   * 获取基金信息
     * @return
     */
    @GetMapping("/getFundInfoList")
    public ResultMessage getFundInfoList(){
        //查询基金信息
    	List<FundInfo> fundList =  fundInfoService.getFundInfoList();
        return ResultMessage.ok(fundList);
    }
    
    /**
             * 获取基金信息
     * @return
     */
    @GetMapping("/getFundInfoByFileName")
    public ResultMessage getFundInfoByFileName(String fileName){
        //查询基金信息
    	FundInfo fundInfo =  fundInfoService.getFundInfoByFileName(fileName);
        return ResultMessage.ok(fundInfo);
    }
}
