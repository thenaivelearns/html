package com.igw.market.systemInfo.service;

import java.util.List;
import java.util.Map;

import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.domain.Management;


public interface ManagementService{
	
	/**
	 * 基础信息管理-用户管理-用户信息list查询 
	 * 传入map键值对，systemId（必选） roleId（可选：选择后返回此角色的用户）
	 * @param map
	 * @return
	 */
	List<Management> getUserList(Map<String, Object> map);
	
	/**
	 * 基础信息管理-角色管理-角色信息list查询
	 * @param systemId
	 * @return
	 */
	List<Management> getRoleList(String systemId);
	
	/**
	 * 基础信息管理-菜单管理-菜单信息list查询
	 * @param systemId
	 * @return
	 */
	List<Management> getMenuList(String systemId);
	
	/**
	 * 基础信息管理-操作管理-查询操作权限 list查询
	 * @param systemId
	 * @return
	 */
	List<Management> getOpList(String systemId);
	
	/**
	 * 基础信息管理-用户管理-重置密码
	 * @param common
	 * @return
	 */
	Integer resetPassword(Common common);
	
	/**
	 * 基础信息管理-用户管理-添加新用户
	 * @param common
	 * @return
	 */
	Integer addNewUser(Common common);
	
	/**
	 * 基础信息管理-用户管理-删除(更新valid_flag=1)用户
	 * @param common
	 * @return
	 */
	Integer deleteUser(Common common);
	
	/**
	 * 基础信息管理-用户管理-恢复(更新valid_flag=0)用户
	 * @param common
	 * @return
	 */
	Integer regainUser(Common common);
	
	/**
	 * 基础信息管理-角色管理-添加新角色
	 * @param common
	 * @return
	 */
	Integer addNewRole(Common common);
	
	/**
	 * 基础信息管理-角色管理-删除角色
	 * @param common
	 * @return
	 */
	Integer deleteRole(Common common);
	
	/**
	 * 基础信息管理-菜单管理-添加新菜单
	 * @param common
	 * @return
	 */
	Integer addNewMenu(Common common);
	
	/**
	 * 基础信息管理-菜单管理-删除菜单
	 * @param common
	 * @return
	 */
	Integer deleteMenu(Common common);
	
	/**
	 * 基础信息管理-操作管理-添加新操作
	 * @param common
	 * @return
	 */
	Integer addNewOp(Common common);
	
	/**
	 * 基础信息管理-操作管理-删除操作
	 * @param common
	 * @return
	 */
	Integer deleteOp(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-获取用户对应的角色信息list
	 * @param systemId
	 * @return
	 */
	List<Management> getUserRoleList(String systemId);
	
	/**
	 * 用户权限管理-角色菜单管理-获取角色对应的菜单list
	 * @param systemId
	 * @return
	 */
	List<Management> getRoleMenuList(String systemId);
	
	/**
	 * 用户权限管理-角色操作管理-获取角色操作list
	 * @param systemId
	 * @return
	 */
	List<Management> getOpRoleList(String systemId);
	
	/**
	 * 用户权限管理-用户角色管理-1.查询用户角色是否存在
	 * @param common
	 * @return
	 */
	Integer checkUserRole(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-2.插入新用户及其角色关系
	 * @param common
	 */
	void addNewUserRole(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-删除(更新valid_flag=1)用户角色关系
	 * @param common
	 * @return
	 */
	Integer deleteUserRole(Common common);

	/**
	 * 基础信息管理-菜单管理-1.查询角色菜单是否存在
	 * @param common
	 * @return
	 */
	Integer checkRoleMenu(Common common);
	
	/**
	 * 基础信息管理-菜单管理-2.插入角色菜单关系
	 * @param common
	 */
	void addRoleMenu(Common common);
	
	/**
	 * 基础信息管理-菜单管理-删除(更新valid_flag=1)角色菜单关系
	 * @param common
	 * @return
	 */
	Integer deleteRoleMenu(Common common);
	
	/**
	 * 用户权限管理-角色操作管理-添加角色操作权限
	 * @param map
	 * @return
	 */
	Map<String, Object> addRoleOp(Map<String, Object> map);
	
	/**
	 * 用户权限管理-角色操作管理-删除角色操作关系
	 * @param common
	 * @return
	 */
	Integer deleteRoleOp(Common common);
	
	/**
	 * 用户登录校验--获取一个用户所有的权限list，存到session中
	 * @param common
	 * @return
	 */
	Map<String, Object> getUserAllOpList(Common common);	
	
	/**
	 * 获取拥有某权限的用户信息
	 * @param map
	 * @return
	 */
	List<Management> getUserOpList(Map<String, Object> map);

	/**
	 * 用户操作权限
	 * @param map
	 * @return
	 */
	List<Management> getUserOpsList(Map<String, Object> map);
	
	
//	
//	/**
//	 * 获取基金信息
//	 */
//	List<Management> getFundList();
//	
//	/**
//	 * 获得产品授权信息
//	 */
//	Map<String, Object> getUserProductList(Map<String, Object> map);
//	
//	/**
//	 *  根据角色删除用户产品关系表，valid_flag设置为1
//	 */
//	public Integer deleteUserProductByRole(Management management);
//	

//	
//	/**
//	 * 根据Productflag获得操作ID
//	 */
//	List<Management> getOperateIdByProductflag(Map<String, Object> map);
}
