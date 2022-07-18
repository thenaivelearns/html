package com.igw.market.institutional.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.util.ResultMessage;
import com.igw.market.institutional.service.InstitutionalTradeService;

/**
 * 	创建中登数据文件
 * 
 */
@RestController
@RequestMapping("createFile")
public class CreateZDDataController {

	@Autowired
	InstitutionalTradeService institutionalTradeService;
	
	/**
	 * 	生成中登数据文件
	 */
	@GetMapping("createFile")
	public ResultMessage<?> createFile(HttpServletRequest request) {
		String fileDate = request.getParameter("fileDate");
		ResultMessage<?> result = institutionalTradeService.createTradeFile(fileDate);
		return result;
	}
}
