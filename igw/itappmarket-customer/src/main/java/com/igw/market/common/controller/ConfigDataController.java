package com.igw.market.common.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.base.common.constant.AppEvnType;
import com.igw.base.common.constant.SystemConstant;
import com.igw.market.common.domain.ResultMessage;

@RestController
@RequestMapping("config")
public class ConfigDataController {
	  
	/**
	 * 获取当前系统环境
	 */
	@PostMapping("getSystemCir")
	public ResultMessage<String> getSystemCir() {
		AppEvnType appEvnType = null;
        String envName = System.getenv(SystemConstant.OP_SYSTEM_ENV_VAR_IGW_ENV_NAME);
        if (envName == null) {
            envName = System.getProperty(SystemConstant.OP_SYSTEM_ENV_VAR_IGW_ENV_NAME);
        }
        if (envName != null) {
            appEvnType = AppEvnType.getEnumByName(envName);
        }
        if (appEvnType == null) {
            appEvnType = AppEvnType.dev;
        }
		return ResultMessage.ok(appEvnType.name());
	}
	
}
