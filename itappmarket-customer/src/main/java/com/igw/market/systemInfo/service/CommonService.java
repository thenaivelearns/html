package com.igw.market.systemInfo.service;


import java.util.List;
import java.util.Map;

import com.igw.market.systemInfo.domain.Common;

public interface CommonService {
	
	/**
	 * 用户登陆验证:根据用户名及密码 校验是否存在 
	 * @param common
	 * @return
	 */
	Map<String, Object> checkUserInfo(Common common);
	
	/**
	 * 用户登陆验证:根据用户名和所属系统验证用户是否锁定
	 * @param common
	 * @return
	 */
	List<Common> checkUserInfoLocks(Common common);
	
	/**
	 * 用户登录验证/修改密码(user_change_pwd_his)：根据用户查询该用户所有修改的密码
	 * @param map
	 * @return
	 */
	List<Common> getUserChangePwdHisList(Map<String,Object> map);
	
	/**
	 * 登陆进入主菜单:获取用户对应的角色及对应的菜单
	 * @param common
	 * @return
	 */
	Map<String, Object> getUserInfo(Common common);
	
	/**
	 * 登陆进入主菜单:获取所有用户中文名
	 * @param map
	 * @return
	 */
	List<Common> getAllUsers(Map<String,Object> map);
	
	/**
	 * 登录相关：用户修改密码
	 * @param common
	 * @return
	 */
	int updPassword(Common common);
	
	/**
	 * 登录相关：用户修改密码(user_change_pwd_his)：将该用户最后一次修改密码数据失效
	 * @param common
	 * @return
	 */
	int updUserChangePwdHis(Common common);
	
	/**
	 * 登录相关：用户修改密码-新增历史(user_change_pwd_his)：新增一条修改密码数据 信息
	 * @param common
	 * @return
	 */
	int addUserChangePwdHis(Common common);
	
	/**
	 *  获取每个人每个业务的提醒数目 
	 * @param userName
	 * @return
	 */
	List<Common> getTodonumByBusi(String userName);
	
	/**
	 * 根据code，获取附件配置信息
	 * @param map
	 * @return
	 */
	Common getAttachmentConfigInfo(Map<String, Object> map);
	
}
