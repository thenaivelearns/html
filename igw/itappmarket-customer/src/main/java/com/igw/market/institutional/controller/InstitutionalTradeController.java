package com.igw.market.institutional.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.WechatUserInfo;
import com.igw.market.common.util.JacksonUtil;
import com.igw.market.common.util.PageUtil;
import com.igw.market.common.util.ResultMessage;
import com.igw.market.institutional.dao.InstitutionalTradeDao;
import com.igw.market.institutional.domain.InstitutionalTrade;
import com.igw.market.institutional.service.InstitutionalTradeService;

/**
 * 机构委托人配置
 * @author aiyongqiang
 *
 */
@RestController
@RequestMapping("institutionalTrade")
public class InstitutionalTradeController {
	
	@Autowired
	private InstitutionalTradeDao institutionalTradeDao;
	
	@Autowired
	private InstitutionalTradeService institutionalTradeService;
	
	/**
	 * 委托人列表
	 * @param body
	 * @return
	 */
	@RequestMapping("list")
	public ResultMessage<PageUtil<InstitutionalTrade>> searchList(@RequestBody String body) {
		Integer page = JacksonUtil.parseInteger(body, "page", 1);
		Integer size = JacksonUtil.parseInteger(body, "size", 10);
		// 查询条件
		Map<String, String> map;
		try {
			map = JacksonUtil.parseObject(body, "query", Map.class);
		} catch (Exception e) {
			map = new HashMap<>();
		}
		PageUtil<InstitutionalTrade> pageUtil = institutionalTradeService.searchList(page, size, map);
		return ResultMessage.ok(pageUtil);
	}
	
    /**
     * 新增委托人
     * @param req
     * @param info
     * @return
     */
	@RequestMapping("add")
	public ResultMessage<String> addInstitutionalTrade(HttpServletRequest req ,@RequestBody InstitutionalTrade info) {
		if(null == info) {
			return ResultMessage.fail("提交对象为空");
		}
		String name = info.getInstitutionalName();
		if(StringUtils.isBlank(name)) {
			return ResultMessage.fail("机构客户名称不能为空");
		}
		// 判断名称是否存在
		Map<String,String> queryMap = new HashMap<>();
		queryMap.put("institutionalName", name);
		List<InstitutionalTrade> list = institutionalTradeDao.getInstitutionalTrade(queryMap);
		if(null != list && list.size() > 0) {
			return ResultMessage.fail("机构客户名称已存在");
		}
		// 操作人
		String userName = (String) req.getSession().getAttribute("userName");
		info.setCreatedUser(userName);
		int i = institutionalTradeDao.add(info);
		if(i > 0) {
			return ResultMessage.ok("创建成功");
		} else {
			return ResultMessage.ok("创建失败");
		}
	}
	
	
	@RequestMapping("/delete")
	public ResultMessage<String> deleteInstitutionalTrade(HttpServletRequest req ,@RequestBody InstitutionalTrade info) {
		if(null == info || StringUtils.isBlank(info.getPkSerial())) {
			return ResultMessage.fail("提交对象为空");
		}
		// 操作人
		String userName = (String) req.getSession().getAttribute("userName");
		info.setUpdatedUser(userName);
		int i = institutionalTradeDao.delete(info);
		if(i > 0) {
			return ResultMessage.ok("删除成功");
		} else {
			return ResultMessage.ok("删除失败");
		}
	}
	
	
	/**
	 * 修改委托人信息
	 * @param req
	 * @param info
	 * @return
	 */
	@RequestMapping("/edit")
	public ResultMessage<String> editInstitutionalTrade(HttpServletRequest req ,@RequestBody InstitutionalTrade info) {
		if(null == info || StringUtils.isBlank(info.getPkSerial())) {
			return ResultMessage.fail("提交对象为空");
		}
		String name = info.getInstitutionalName();
		if(StringUtils.isBlank(name)) {
			return ResultMessage.fail("机构客户名称不能为空");
		}
		// 判断名称是否存在
		Map<String,String> queryMap = new HashMap<>();
		queryMap.put("institutionalName", name);
		List<InstitutionalTrade> list = institutionalTradeDao.getInstitutionalTrade(queryMap);
		if(null != list) {
			if(list.size() > 1) {
				return ResultMessage.fail("机构客户名称已存在");
			}
			InstitutionalTrade old = list.get(0);
			if(!info.getPkSerial().equals(old.getPkSerial())) {
				return ResultMessage.fail("机构客户名称已存在");
			}
		}
		// 操作人
		String userName = (String) req.getSession().getAttribute("userName");
		info.setUpdatedUser(userName);
		int i = institutionalTradeDao.edit(info);
		if(i > 0) {
			return ResultMessage.ok("修改成功");
		} else {
			return ResultMessage.ok("修改失败");
		}
	}
	
	

}
