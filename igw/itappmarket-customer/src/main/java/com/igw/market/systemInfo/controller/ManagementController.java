package com.igw.market.systemInfo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.domain.Management;
import com.igw.market.systemInfo.service.CommonService;
import com.igw.market.systemInfo.service.ManagementService;

@RestController
public class ManagementController {

	@Autowired
	ManagementService managementService;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 初始化【基础信息管理】页面
	 * @param req
	 * @param modelMap
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value = "/infomanagement")
	public ResultMessage infomanagement(HttpServletRequest req, ModelMap modelMap) {
    	ResultMessage msg = new ResultMessage();
    	
		Map<String, Object> map = new HashMap<String, Object>();
		String systemId = (String) req.getSession().getAttribute("systemId");
		map.put("systemId", systemId);
		map.put("validFlag", "all");
		List<Management> userList = managementService.getUserList(map);
		modelMap.put("userList", userList);
		
		List<Management> roleList = managementService.getRoleList(systemId);
		modelMap.put("roleList", roleList);
		
		List<Management> menuList = managementService.getMenuList(systemId);
		modelMap.put("menuList", menuList);
		
		List<Management> opsList = managementService.getOpList(systemId);
		modelMap.put("opsList", opsList);
		
		msg.setData(modelMap);
		msg.setMsgCode("Y");
		msg.setMsgDesc("初始化基础信息管理页面成功!");
		
		return msg;
	}
    
	/**
	 * 基础信息管理-用户管理-重置密码
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/resetPassword")
	public ResultMessage resetPassword(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		String userPk=res.get("userPk").toString();
		
		Common common = new Common();
		common.setPkSerial(userPk);
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setNewPasswordNo("c4ca4238a0b923820dcc509a6f75849b");
		
		// 更新该记录的valid_flag为1
		if (managementService.resetPassword(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("重置密码失败");
		}

		return msg;
	}

	/**
	 * 基础信息管理-用户管理-添加新用户
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertNewUser")
	public ResultMessage addNewUser(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		String userId=res.get("userId").toString();
		String accountName=res.get("accountName").toString();
		
		Common common = new Common();
		common.setAccountId(userId.toLowerCase());
		common.setAccountName(accountName.toLowerCase());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		
		// 查询用户名及其角色是否存在
		if (managementService.addNewUser(common) == 0) {
			// 插入新用户成功
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("该用户已经存在，请重新输入");
		}
		return msg;
	}
	
	/**
	 * 基础信息管理-用户管理-删除用户
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteUser")
	public ResultMessage deleteUser(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		String userPk=res.get("userPk").toString();
		
		Common common = new Common();
		common.setPkSerial(userPk);
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteUser(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除用户失败");
		}

		return msg;
	}
	
	/**
	 * 基础信息管理-用户管理-恢复用户
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/regainUser")
	public ResultMessage regainUser(@RequestBody Map<String, Object> res,  HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		String pkSerial=res.get("pkSerial").toString();
		String accountId=res.get("accountId").toString();
		Common common=new Common();
		common.setPkSerial(pkSerial);
		common.setAccountId(accountId.toLowerCase());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		// 更新该记录的valid_flag为0
		int num=managementService.regainUser(common);
		if (num==1) {
			msg.setMsgCode("Y");
		}else if (num==2) {
			msg.setMsgCode("N");
			msg.setMsgDesc("存在该用户，无法恢复此账户");
		}else {
			msg.setMsgCode("N");
			msg.setMsgDesc("恢复用户失败");
		}

		return msg;
	}
	
	/**
	 * 基础信息管理-角色管理-添加新角色
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertNewRole")
	public ResultMessage addNewRole(@RequestBody Map<String, Object> res,  HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		String roleName=res.get("roleName").toString();
		String roleId=res.get("roleId").toString();
		Common common = new Common();
		common.setRoleName(roleName);
		common.setRoleId(roleId);
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		
		// 查询角色ID是否存在
		if (managementService.addNewRole(common) == 0) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("此角色ID已存在");
		}

		return msg;
	}
	
	/**
	 * 基础信息管理-角色管理-删除角色
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteRole")
	public ResultMessage deleteRole(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		String rolePk=res.get("rolePk").toString();
		Common common = new Common();
		common.setRolePk(rolePk);
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteRole(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除角色失败");
		}
		return msg;
	}
	
	/**
	 * 基础信息管理-菜单管理-添加新菜单
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertMenu")
	public ResultMessage addNewMenu(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setMenuId(res.get("menuId").toString());
		common.setMenuName(res.get("menuName").toString());
		common.setMenuLevel(res.get("menuLevel").toString());
		common.setMenuUrl(res.get("menuUrl").toString());
		common.setMenuPre(res.get("menuPre").toString());
		
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		
		// 查询菜单ID是否存在
		if (managementService.addNewMenu(common) == 0) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("此菜单ID已存在");
		}

		return msg;
	}
	
	
	/**
	 * 基础信息管理-菜单管理-删除菜单
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteMenu")
	public ResultMessage deleteMenu(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setMenuPk(res.get("menuPk").toString());
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteMenu(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除菜单失败");
		}

		return msg;
	}
	
	/**
	 * 基础信息管理-操作管理-添加新操作
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertOp")
	public ResultMessage addNewOp(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setOperateId(res.get("operateId").toString());
		common.setOperateName(res.get("operateName").toString());
		
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		
		// 查询菜单ID是否存在
		if (managementService.addNewOp(common) == 0) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("此菜单ID已存在");
		}

		return msg;
	}
	
	/**
	 * 基础信息管理-操作管理-删除操作
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteOp")
	public ResultMessage deleteOp(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setPkSerial(res.get("pkSerial").toString());
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		common.setUserName((String) req.getSession().getAttribute("userName"));
	
		// 更新该记录的valid_flag为1
		if (managementService.deleteOp(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除菜单失败");
		}

		return msg;
	}
	
	/**
	 * 初始化【用户权限管理】页面
	 * @param req
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/permission")
	public ResultMessage permission(HttpServletRequest req, ModelMap modelMap) {
		ResultMessage msg = new ResultMessage();
		
		// 查询系统下所有的用户及其角色
		String systemId = (String) req.getSession().getAttribute("systemId");
		
		//获取用户角色信息
		List<Management> userRolelist = managementService.getUserRoleList(systemId);
		modelMap.addAttribute("userRolelist", userRolelist);
		
		// 获取角色信息
		List<Management> rolelist = managementService.getRoleList(systemId);
		modelMap.addAttribute("rolelist", rolelist);
		
		// 获取角色菜单信息
		List<Management> roleMenulist = managementService.getRoleMenuList(systemId);
		modelMap.addAttribute("roleMenulist", roleMenulist);
		
		// 获取菜单信息
		List<Management> menulist = managementService.getMenuList(systemId);
		modelMap.addAttribute("menulist", menulist);
		
		// 操作角色列表
		List<Management> opRolelist = managementService.getOpRoleList(systemId);
		modelMap.addAttribute("opRolelist", opRolelist);
		
		// 获取操作权限信息
		List<Management> oplist = managementService.getOpList(systemId);
		modelMap.addAttribute("oplist", oplist);
		
		// 操作角色列表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemId", systemId);
		List<Management> userlist = managementService.getUserList(map);
		modelMap.addAttribute("userlist", userlist);

		msg.setData(modelMap);
		msg.setMsgCode("Y");
		msg.setMsgDesc("初始化用户权限管理页面成功!");
		return msg;
	}
	
	/**
	 * 用户权限管理-用户角色管理-添加用户角色关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertUserRole")
	public ResultMessage addNewUserRole(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setAccountId(res.get("userId").toString());
		common.setRoleId(res.get("rolePk").toString());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		common.setSystemId((String) req.getSession().getAttribute("systemId"));
		
		// 查询用户名及其角色是否存在
		if (managementService.checkUserRole(common) == 0) {
			// 插入新用户成功
			managementService.addNewUserRole(common);
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("该用户已经存在该角色，请重新输入");
		}

		return msg;
	}
	
	/**
	 * 用户权限管理-用户角色管理-删除用户角色关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteUserRole")
	public ResultMessage deleteUserRole(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setPkSerial(res.get("userPk").toString());
		common.setRolePk(res.get("rolePk").toString());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteUserRole(common) == 1) {
			// 插入新用户成功
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除失败");
		}

		return msg;
	}
	
	/**
	 * 用户权限管理-角色菜单管理-添加角色菜单关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertRoleMenu")
	public ResultMessage addRoleMenu(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setMenuPk(res.get("menuPk").toString());
		common.setRolePk(res.get("rolePk").toString());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 查询用户名及其角色是否存在
		if (managementService.checkRoleMenu(common) == 0) {
			// 插入新用户成功
			managementService.addRoleMenu(common);
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("该角色已经有该菜单权限，请重新输入");
		}

		return msg;
	}
	
	/**
	 * 用户权限管理-角色菜单管理-删除角色菜单关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteRoleMenu")
	public ResultMessage deleteRoleMenu(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setMenuPk(res.get("menuPk").toString());
		common.setRolePk(res.get("rolePk").toString());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteRoleMenu(common) == 1) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除失败");
		}

		return msg;
	}
	
	
	/**
	 * 用户权限管理-角色操作管理-添加角色操作关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/insertRoleOp")
	public ResultMessage addRoleOp(@RequestBody Map<String, Object> map, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("rolePk", req.getParameter("rolePk"));
//		map.put("opPks", req.getParameter("opPks"));
		map.put("userName", req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		Map<String, Object> mapResult = managementService.addRoleOp(map);
		if (mapResult.get("msgCode").equals("Y")) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc((String)mapResult.get("msgDesc"));
		}

		return msg;
	}
	
	/**
	 * 用户权限管理-角色操作管理-删除角色操作关系
	 * @param response
	 * @param req
	 * @throws IOException
	 * @throws SecurityException
	 * @throws MapperException
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteRoleOp")
	public ResultMessage deleteRoleOp(@RequestBody Map<String, Object> res, HttpServletRequest req)
			throws IOException, SecurityException {
		ResultMessage msg = new ResultMessage();
		
		Common common = new Common();
		common.setRolePk(res.get("rolePk").toString());
		common.setUserName((String) req.getSession().getAttribute("userName"));
		
		// 更新该记录的valid_flag为1
		if (managementService.deleteRoleOp(common) > 0) {
			msg.setMsgCode("Y");
		} else {
			msg.setMsgCode("N");
			msg.setMsgDesc("删除失败");
		}

		return msg;
	}
	
	/**
	 * 查询：获取拥有某权限的用户信息
	 * @param res
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserOpList")
	public ResultMessage getUserOpList(@RequestBody Map<String, Object> param, HttpServletRequest req) {
		
		// 操作人
		String userName = (String) req.getSession().getAttribute("userName");
		// 系统ID
		String systemId = (String) req.getSession().getAttribute("systemId");
		// 权限
		String roleId=(String) param.get("roleId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemId", systemId);
		if(StringUtils.isNotEmpty(roleId)) {// 为空查所有用户
			map.put("roleId","'" + roleId.replace(";", "','")+ "'");
		}
		List<Management> userList = managementService.getUserOpList(map);
		
		// 返回结果Map
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 获取拥有某权限的用户信息
		resMap.put("userList", userList);
		// 当前操作人
		resMap.put("userName", userName);
		
		return ResultMessage.ok(resMap);
	}
	
	
}
