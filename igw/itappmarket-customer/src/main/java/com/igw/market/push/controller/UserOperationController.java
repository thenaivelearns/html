package com.igw.market.push.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.push.dao.UserOperationDao;
import com.igw.market.push.dto.UserOperationDTO;

@RestController
@RequestMapping("userOperation")
public class UserOperationController {

	@Autowired
	private UserOperationDao userOperationDao;
	
	
	@RequestMapping("getUserOperation")
	public ResultMessage getUserOperation() {
		List<UserOperationDTO> list =  userOperationDao.getOpenation();
		return ResultMessage.ok(list);
	}
	
}
