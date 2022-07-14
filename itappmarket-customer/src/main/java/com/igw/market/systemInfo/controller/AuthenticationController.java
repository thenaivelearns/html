package com.igw.market.systemInfo.controller;

import static com.igw.base.common.constant.PathConstant.URI_LOGIN_PAGE;
import static com.igw.base.common.constant.PathConstant.URI_ROOT;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.DateUtil;
import com.igw.market.common.util.MD5Util;
import com.igw.market.common.util.PropertiesUtil;
import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.domain.IgwUserDto;
import com.igw.market.systemInfo.domain.Management;
import com.igw.market.systemInfo.service.CommonService;
import com.igw.market.systemInfo.service.ManagementService;

import net.sf.json.JSONArray;


@RestController
public class AuthenticationController {
	
    @Autowired
	ManagementService managementService;

    @Autowired
    private CommonService commonService;
    
    private static final Logger sendLog = Logger.getLogger(AuthenticationController.class);
    /**
     * 获取系统根路径
     * @return
     */
    @GetMapping(URI_ROOT)
    public void reqRootPath(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/static/page/home/login.html");
    }
    
    /**
   	 * 用户登陆验证
     */	
   	@ResponseBody
    @RequestMapping(value = "/loginToSystem")
   	public ResultMessage login(@RequestBody @Valid IgwUserDto igwUserDto,HttpServletRequest req) throws Exception{
   		
   		ResultMessage msg = new ResultMessage();
   		
   		String systemId = PropertiesUtil.getCfg("project.name");
   		
   	    //获取参数
   		String userName = igwUserDto.getUserName();
   		userName = userName.toLowerCase();
   		String password = igwUserDto.getPassword();
   		
   	    //转换密码为MD5
		Map<String, Object> map = new HashMap<String, Object>();
		Common common = new Common();
		common.setAccountId(userName);
		common.setPasswordNo(MD5Util.getMD5String(password));
		common.setSystemId(systemId);
		// 根据用户名及密码 校验是否存在
		map = commonService.checkUserInfo(common);
		int userInfoNum = Integer.parseInt(String.valueOf(map.get("userInfoNum")));
		
		//获取用户最后修改密码日期
		Map<String, Object> mapPwd = new HashMap<String, Object>();
		mapPwd.put("accountId",userName);
		mapPwd.put("systemId",systemId);
		mapPwd.put("validFlag", "0");
		List<Common> listHisPwd= commonService.getUserChangePwdHisList(mapPwd);
		
		// 获取用户锁定次数
		String locksNum="0";
		List<Common> locksList= commonService.checkUserInfoLocks(common);
		if(locksList!=null&&locksList.size()>0){
			Common mon=  locksList.get(0);
			String date=DateUtil.getYYYYMMDD();
			String lockdate=mon.getLocksDate();
			// 用户锁定日期和当前相同
			if(StringUtils.isNotEmpty(lockdate)){
				if(date.equals(lockdate)){
					locksNum=mon.getLocksNum();
				}
			}
		}
		
		// 判断用户是否锁定（三次为锁定）
		if(Integer.valueOf(locksNum)>=3){
			msg.setMsgCode("L");
			msg.setMsgDesc("当日已三次密码错误，用户已锁定，请联系管理员解锁。");
			sendLog.info("当日已三次密码错误，用户已锁定，请联系管理员解锁："+userName);
		}else{
			
			//超过90天则需修改密码
			boolean flagPwd= true;
			if(listHisPwd!=null&&listHisPwd.size()>0){
				//最后一次修改日期
				String changeDate=listHisPwd.get(0).getChangeDate();
				String numDate=DateUtil.getDateSubtract(changeDate,DateUtil.getYYYYMMDD());
				if(Integer.valueOf(numDate)>90){
					flagPwd=false;
				}                      
			}
			
			//验证用户名、密码。后续增加角色、权限等
			if(userInfoNum > 0) {
				// 往session里面添加权限信息
				for(Management dto:(List<Management>)(managementService.getUserAllOpList(common)).get("opList")){
					if(dto.getValue()!=null){
						req.getSession().setAttribute(dto.getMark(), dto.getValue());
						sendLog.info("OPS:"+dto.getMark()+"-"+dto.getValue());
					}
				}
				// 绑定用户名至session
				req.getSession().setAttribute("userName", userName);
				// 绑定系统ID至session
				req.getSession().setAttribute("systemId", systemId);
				req.getSession().setMaxInactiveInterval(28800);
				/*if("1".equals(password)){
					msg.setMsgCode("T");
					msg.setMsgDesc("不可使用初始密码登录，需要修改密码。");
					sendLog.info("不可使用初始密码登录，需要修改密码："+account);
			    }else {*/
			    	//超过90天则需要修改密码
			    	if(!flagPwd){
			    		msg.setMsgCode("T");
						msg.setMsgDesc("密码已到期(90天未修改密码则过期)，请修改密码。");
						sendLog.info("密码已到期(90天未修改密码则过期)，请修改密码："+userName);
			    	}else{
				    	msg.setMsgCode("Y");
						msg.setMsgDesc("登陆成功!");
						sendLog.error((new Date())+"账号登录成功："+userName);
			    	}
//			    }
				// 登出该账号在其他地方地登录号
				// WebsocketEndPoint wbs = new WebsocketEndPoint();
				// wbs.sendMessageToUser(account, "logout");
			}else {
				// 用户锁定第几次
				int nums=Integer.valueOf(locksNum)+1;
				
				// 记录用户错误锁定次数与日期
				Common com = new Common();
				com.setAccountId(userName);
				com.setLocksNum(String.valueOf(nums));
				com.setLocksDate(DateUtil.getYYYYMMDD());
				com.setSystemId(systemId);
				commonService.updPassword(com);
				
				msg.setMsgCode("N");
				msg.setMsgDesc("用户名或密码不正确!");
			}
		}
   	    return msg;	
   	}
   	
    /**
     * 登录相关：用户修改密码
     * @param res
     * @param req
     * @return
     * @throws Exception
     */
   	@ResponseBody
    @RequestMapping(value = "/updPassword")
	public ResultMessage updPassword(@RequestBody Map<String, Object> res,HttpServletRequest req ) throws Exception {
   		
   		ResultMessage msg = new ResultMessage();
   		
		//获取参数
		Common common = new Common();
		common.setNewPasswordNo(MD5Util.getMD5String(res.get("newPasswordNo").toString()));
		common.setPasswordNo(MD5Util.getMD5String(res.get("passwordNo").toString()));
		common.setAccountId(req.getSession().getAttribute("userName").toString());
		common.setSystemId(req.getSession().getAttribute("systemId").toString());
		
		//获取用户曾用过的所有密码
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("accountId",req.getSession().getAttribute("userName").toString());
		map2.put("systemId",req.getSession().getAttribute("systemId").toString());
		List<Common> listHisPwd= commonService.getUserChangePwdHisList(map2);
		boolean flagPwd= true;
		if(listHisPwd!=null&&listHisPwd.size()>0){
			for(Common comm:listHisPwd){
				//新密码和用过的密码相同
	    		if((MD5Util.getMD5String(res.get("newPasswordNo").toString())).equals(comm.getPasswordNo())){
	    			flagPwd=false;
		    	}
	    	}
		}
		
		if(flagPwd){
			//修改密码
			int i = commonService.updPassword(common);
			//验证用户名、密码。后续增加角色、权限等
			if(i > 0) {
				//将此次修改前的修改过的密码置为失效1
				commonService.updUserChangePwdHis(common);
				//保存每次修改的密码
				common.setChangeDate(DateUtil.getYYYYMMDD());
				commonService.addUserChangePwdHis(common);
				
				msg.setMsgCode("Y");
				sendLog.info("修改密码成功："+req.getSession().getAttribute("userName"));
			}else {
				msg.setMsgCode("N");
				msg.setMsgDesc("用户名或密码不正确!");
				sendLog.info("修改密码失败，原始密码输入错误。"+req.getSession().getAttribute("userName"));
			}
		}else{
			msg.setMsgCode("N");
			msg.setMsgDesc("新密码不能与曾用过的密码相同！");
			sendLog.info("修改密码失败，新密码不能与曾用过的密码相同。"+req.getSession().getAttribute("userName"));
		}
    	
		 return msg;	
	}
   	
   	/**
	 * 登陆进入主菜单
	 * @throws UnknownHostException 
     */	
	@ResponseBody
    @RequestMapping(value = "/loginHome")
	public ResultMessage home(ModelMap modelMap,HttpServletRequest req) throws UnknownHostException {
		ResultMessage msg = new ResultMessage();
		
		//获取用户所属的角色  及对应的菜单
		Map<String, Object> userMap = new HashMap<String, Object>();
		String userName = (String) req.getSession().getAttribute("userName");
		Common common = new Common();
		common.setAccountId(userName);
		String systemId = PropertiesUtil.getCfg("project.name");
		common.setSystemId(systemId);
		userMap = commonService.getUserInfo(common);

		//解析获取的 角色信息及菜单信息
		List<Common> userRoleList = (List<Common>)userMap.get("userRoleList");
		req.getSession().setAttribute("userRoleList", userRoleList);
		modelMap.put("userRoleList",userRoleList);
		List<Common> roleMenuList = (List<Common>)userMap.get("roleMenuList");
		req.getSession().setAttribute("roleMenuList", JSONArray.fromObject(getMenu(roleMenuList)));
		modelMap.put("roleMenuList",JSONArray.fromObject(getMenu(roleMenuList)));
		
		// 获取提示信息
		//List<Common> todoList = commonService.getTodonumByBusi(userName);
		//modelMap.put("todoList",JSONArray.fromObject(getTodoList(todoList)));
		
		//系统名称
		modelMap.put("systemName", systemId);
		if(userRoleList.size() > 0){
			modelMap.put("cuserName",(String) userRoleList.get(0).getAccountName());
			req.getSession().setAttribute("cuserName", (String) userRoleList.get(0).getAccountName());
		}
		
		//获取所有用户中文名
		userMap.put("accountId", req.getSession().getAttribute("userName"));
		userMap.put("systemId",systemId);
		List<Common> nameList = commonService.getAllUsers(userMap);
		modelMap.put("nameList", nameList);
		
		// 获取用户操作权限
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取拥有某权限的用户信息
		map.put("systemId", systemId);
		// 当前操作人
		map.put("userName", userName);
		List<Management> opsList = managementService.getUserOpsList(map);
		
		//获取的是本地的IP地址和端口
		modelMap.put("ipAddress", req.getLocalAddr()+":"+req.getServerPort());
		modelMap.put("userName", userName);
		modelMap.put("opsList", opsList);
		modelMap.put("sysDate", DateUtil.getYYYYMMDDCh(new Date()));
		
		//放入权限信息,是否为会计岗
		/*String rolesStr = (String) req.getSession().getAttribute("ROLES");
		String[] rolesAry = rolesStr.split(",");
		modelMap.put("isFa", false);
		for(String str : rolesAry) {
			if(str.equals("FA") || str.equals("FA_OP")) {
				modelMap.put("isFa", true);
			}
		}*/
		
		msg.setData(modelMap);

		return msg;
	}
	
	/**
	 * 登陆进入主菜单:组装菜单
	 * @param roleMenuList
	 * @return
	 */
	List<Map<String, Object>> getMenu(List<Common> roleMenuList) {
		// 返回的菜单
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		if (roleMenuList != null && roleMenuList.size() > 0) {
			// 一级菜单（包含二级菜单）
			HashMap<String, Object> file = new HashMap<String, Object>();
			// 二级菜单
			List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
			String menuPre = roleMenuList.get(0).getMenuPre();
			for (int i = 0; i < roleMenuList.size(); i++) {
				Common dto = roleMenuList.get(i);
				if (menuPre.equals(dto.getMenuPre())) {
					// 一级菜单路径不为空
					if("1".equals(dto.getMenuLevel())&&dto.getMenuUrl() != null) {
						file.put("title", dto.getMenuName());
						file.put("url", dto.getMenuUrl());
					}else {
						// 同一级的菜单
						if (dto.getMenuUrl() == null || "".equals(dto.getMenuUrl())) {
							// 一级菜单
							file.put("title", dto.getMenuName());
							file.put("url", "");
						}else {
							// 二级菜单
							HashMap<String, Object> menu1_1 = new HashMap<String, Object>();
							menu1_1.put("title", dto.getMenuName());
							menu1_1.put("url", dto.getMenuUrl());
							data.add(menu1_1);
						}
					}
				} else {
					// 不同级菜单
					menuPre = dto.getMenuPre();
					file.put("data", data);
					menu.add(file);
					file = new HashMap<String, Object>();
					data = new ArrayList<HashMap<String, Object>>();
					if("1".equals(dto.getMenuLevel())&&dto.getMenuUrl() != null) {
						file.put("title", dto.getMenuName());
						file.put("url", dto.getMenuUrl());
					}else {
						if (dto.getMenuUrl() == null || "".equals(dto.getMenuUrl())) {
							// 一级菜单
							file.put("title", dto.getMenuName());
							file.put("url", "");
						} else {
							// 二级菜单
							HashMap<String, Object> menu1_1 = new HashMap<String, Object>();
							menu1_1.put("title", dto.getMenuName());
							menu1_1.put("url", dto.getMenuUrl());
							data.add(menu1_1);
						}
					}
				}
			}
			file.put("data", data);
			menu.add(file);
		}

		return menu;
	}
	
	/**
	 * 查询：获取系统所有用户中文名
	 * @param param
	 * @param req
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/getAllUsers")
    public ResultMessage getAllUsers(HttpServletRequest req){

		// 查询系统所有用户中文名
		Map<String, Object> map = new HashMap<String, Object>();
		// 系统名称
		map.put("systemId",PropertiesUtil.getCfg("project.name"));
		List<Common> commonList=commonService.getAllUsers(map);
    	if(commonList==null||commonList.size()==0) {
    		return ResultMessage.fail("未查找到 系统用户名 的数据");
    	}
    	
    	return ResultMessage.ok(commonList);
    }
	
    /**
     * 登出
     * @param req
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout")
	public ResultMessage logout(HttpServletRequest req , HttpServletResponse response) {
    	
    	ResultMessage msg = new ResultMessage();
		//清空登陆信息
		HttpSession session = req.getSession();
		if (session.getAttribute("userName") != null) {
			session.removeAttribute("userName");
			session.removeAttribute("userRoleList");
			session.removeAttribute("roleMenuList");
			msg.setMsgCode("Y");
			msg.setMsgDesc("登出成功!");
        }
		
		return msg;
	}
	/*@PostMapping(value =  "/logout")
    public ResponseEntity logout(HttpServletRequest req) throws ServiceException {
        if (req.getSession().getAttribute("userName") != null) {
            //若用户身份信息存在session中，则清除之，并返回 200 OK 状态码。
            req.getSession().removeAttribute("userName");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            //若用户身份信息不存在session中，则抛出异常，提示用户之前已经退出。最终用户都会处于离线状态。
            throw new ServiceException(CommonServiceExceptionEnum.PORTAL_ALREADY_LOGOUT_BEFORE);
        }
    }*/

}
