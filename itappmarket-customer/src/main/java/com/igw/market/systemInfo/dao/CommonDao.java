package com.igw.market.systemInfo.dao;

import java.util.List;
import java.util.Map;

import com.igw.market.systemInfo.domain.Common;

public interface CommonDao {
	

	
	/**
	 * 用户登陆验证:根据用户名及密码 校验是否存在 
	 * @param common
	 * @return
	 */
	public int checkUserInfo(Common common);
	
	/**
	 * 用户登陆验证:根据用户名和所属系统验证用户是否锁定
	 * @param common
	 * @return
	 */
	public List<Common> checkUserInfoLocks(Common common);
	
	/**
	 * 用户登录验证/修改密码(user_change_pwd_his)：根据用户查询该用户所有修改的密码
	 * @param map
	 * @return
	 */
	public List<Common> getUserChangePwdHisList(Map<String,Object> map);
	
	/**
	 * 登陆进入主菜单:获取用户对应的角色信息
	 * @param common
	 * @return
	 */
	public List<Common> getUserRoleList(Common common);
	
	/**
	 * 登陆进入主菜单:获取角色对应的菜单信息
	 * @param common
	 * @return
	 */
	public List<Common> getRoleMenuList(Common common);
	
	/**
	 * 登陆进入主菜单:获取所有用户中文名
	 * @param map
	 * @return
	 */
	public List<Common> getAllUsers(Map<String,Object> map);
	
	/**
	 * 登录相关：用户修改密码
	 * @param common
	 * @return
	 */
	public int updPassword(Common common);
	
	/**
	 * 登录相关：用户修改密码(user_change_pwd_his)：将该用户最后一次修改密码数据失效
	 * @param common
	 * @return
	 */
	public int updUserChangePwdHis(Common common);
	
	/**
	 * 登录相关：用户修改密码-新增历史(user_change_pwd_his)：新增一条修改密码数据 信息
	 * @param common
	 * @return
	 */
	public int addUserChangePwdHis(Common common);
	
	/**
	 * 获取每个人每个业务的提醒数目 
	 * @param map
	 * @return
	 */
	public List<Common> getTodonumByBusi(Map<String, Object> map);
	
	/**
	 * 根据code，获取附件配置信息
	 * @param map
	 * @return
	 */
	public Common getAttachmentConfigInfo(Map<String, Object> map);
	
}
