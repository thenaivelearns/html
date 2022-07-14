package com.igw.market.systemInfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igw.base.common.annotation.CommonLog;
import com.igw.market.common.util.DateUtil;
import com.igw.market.systemInfo.dao.CommonDao;
import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.service.CommonService;


@Service("commonService")
public class CommonServiceImpl implements CommonService {


	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CommonDao commonDao;

	@Override
	@CommonLog
	public Map<String, Object> checkUserInfo(Common common) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户登陆验证:根据用户名及密码 校验是否存在 
		int userInfoNum = commonDao.checkUserInfo(common);
		map.put("userInfoNum", userInfoNum);
		
		return map;
	}
	
	@Override
	@CommonLog
	public List<Common> checkUserInfoLocks(Common common){
		// 用户登陆验证:根据用户名和所属系统验证用户是否锁定
		return commonDao.checkUserInfoLocks(common);
	}
	
	@Override
	@CommonLog
	public List<Common> getUserChangePwdHisList(Map<String,Object> map){
		// 用户登录验证/修改密码(user_change_pwd_his)：根据用户查询该用户所有修改的密码
		return commonDao.getUserChangePwdHisList(map);
	}
	
	@Override
	@CommonLog
	public Map<String, Object> getUserInfo(Common common) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 登陆进入主菜单:获取用户对应的角色信息
		List<Common> userRoleList = commonDao.getUserRoleList(common);

		// 登陆进入主菜单:获取用户对应的菜单信息
		String roleId = "(";
		if (userRoleList != null && userRoleList.size() > 0) {
			for (int i = 0; i < userRoleList.size(); i++) {
				Common dto = userRoleList.get(i);
				roleId = roleId + "'" + dto.getRolePk() + "',";
			}
			roleId = roleId.substring(0, roleId.lastIndexOf(","));
			roleId = roleId + ")";
		} else {
			roleId = "('')";
		}
		common.setRoleId(roleId);
		List<Common> roleMenuList = commonDao.getRoleMenuList(common);

		map.put("userRoleList", userRoleList);
		map.put("roleMenuList", roleMenuList);
		return map;
	}
	
	@Override
	@CommonLog
	public List<Common> getAllUsers(Map<String,Object> map) {
		// 登陆进入主菜单:获取所有用户中文名
		return commonDao.getAllUsers(map);
	}
	
	@Override
	@CommonLog
    public int updPassword(Common common) {
		// 登录相关：用户修改密码
		return commonDao.updPassword(common);
	}
    
	@Override
	@CommonLog
    public int updUserChangePwdHis(Common common){
		// 登录相关：用户修改密码(user_change_pwd_his)：将该用户最后一次修改密码数据失效
		return commonDao.updUserChangePwdHis(common);
	}

	@Override
	@CommonLog
	public int addUserChangePwdHis(Common common){
		// 登录相关：用户修改密码-新增历史(user_change_pwd_his)：新增一条修改密码数据 信息
		return commonDao.addUserChangePwdHis(common);
	}
	
	@Override
	@CommonLog
	public List<Common> getTodonumByBusi(String userName){
		// 获取每个人每个业务的提醒数目 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("tDate", DateUtil.getYYYYMMDD());
		return commonDao.getTodonumByBusi(map);
	}
	
	@Override
	@CommonLog
	public Common getAttachmentConfigInfo(Map<String, Object> map){
		// 根据code，获取附件配置信息
		return commonDao.getAttachmentConfigInfo(map);
	}

}
