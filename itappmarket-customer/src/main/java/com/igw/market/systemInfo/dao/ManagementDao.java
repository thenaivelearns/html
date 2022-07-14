package com.igw.market.systemInfo.dao;

import java.util.List;
import java.util.Map;

import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.domain.Management;

public interface ManagementDao {
	
	/**
	 * 基础信息管理-用户管理-用户信息list查询 
	 * @param map
	 * @return
	 */
	public List<Management> getUserList(Map<String, Object> map);
	
	/**
	 * 基础信息管理-角色管理-角色信息list查询
	 * @param systemId
	 * @return
	 */
	public List<Management> getRoleList(String systemId);
	
	/**
	 * 基础信息管理-菜单管理-菜单信息list查询
	 * @param systemId
	 * @return
	 */
	public List<Management> getMenuList(String systemId);
	
	/**
	 * 基础信息管理-操作管理-查询操作权限 list查询
	 * @param map
	 * @return
	 */
	public List<Management> getOpList(Map<String, Object> map);
	
	/**
	 * 基础信息管理-用户管理-重置密码
	 * @param common
	 * @return
	 */
	public Integer resetPassword(Common common);
	
	/**
	 * 基础信息管理-用户管理-1.查询用户名是否存在
	 * @param common
	 * @return
	 */
	public int checkUserName(Common common);
	
	/**
	 * 基础信息管理-用户管理-2.插入新的用户,默认密码是1
	 * @param common
	 */
	public void insertNewUser(Common common);
	
	/**
	 * 基础信息管理-用户管理-删除(更新valid_flag=1)用户
	 * @param common
	 * @return
	 */
	public Integer deleteUser(Common common);
	
	/**
	 * 基础信息管理-用户管理-恢复(更新valid_flag=0)用户
	 * @param common
	 * @return
	 */
	public Integer regainUser(Common common);
	
	/**
	 * 基础信息管理-角色管理-1.查询角色ID是否存在
	 * @param common
	 * @return
	 */
	public int checkRoleId(Common common);
	
	/**
	 * 基础信息管理-角色管理-2.新增新角色
	 * @param common
	 */
	public void insertNewRole(Common common);
	
	/**
	 * 基础信息管理-角色管理-删除(更新valid_flag=1)角色
	 * @param common
	 * @return
	 */
	public Integer deleteRole(Common common);
	
	/**
	 * 查询菜单ID是否存在
	 * @param common
	 * @return
	 */
	public int checkMenuId(Common common);
	
	/**
	 * 基础信息管理-菜单管理-2.新增菜单
	 * @param common
	 */
	public void insertNewMenu(Common common);
	
	/**
	 * 基础信息管理-菜单管理-删除(更新valid_flag=1)菜单
	 * @param common
	 * @return
	 */
	public Integer deleteMenu(Common common);
	
	/**
	 * 基础信息管理-操作管理-1.查询操作ID是否存在
	 * @param common
	 * @return
	 */
	public int checkOpId(Common common);
	
	/**
	 * 基础信息管理-操作管理-2.新增新操作
	 * @param common
	 */
	public void insertNewOp(Common common);
	
	/**
	 * 基础信息管理-操作管理-删除(更新valid_flag=1)操作
	 * @param common
	 * @return
	 */
	public Integer deleteOp(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-获取用户对应的角色信息list
	 * @param systemId
	 * @return
	 */
	public List<Management> getUserRoleList(String systemId);
	
	/**
	 * 用户权限管理-角色菜单管理-获取角色对应菜单信息list
	 * @param systemId
	 * @return
	 */
	public List<Management> getRoleMenuList(String systemId);
	
	/**
	 * 用户权限管理-角色操作管理-获取角色操作list
	 * @param map
	 * @return
	 */
	public List<Management> getOpRoleList(Map<String, Object> map);
	
	/**
	 * 用户权限管理-用户角色管理-1.查询用户角色关系是否存在
	 * @param common
	 * @return
	 */
	public int checkUserRole(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-2.新增用户角色关系
	 * @param common
	 */
	public void insertNewUserRole(Common common);
	
	/**
	 * 用户权限管理-用户角色管理-删除(更新valid_flag=1)用户角色关系
	 * @param common
	 * @return
	 */
	public Integer deleteUserRole(Common common);
	
	/**
	 * 用户权限管理-角色菜单管理-1.查询角色菜单关系是否存在
	 * @param common
	 * @return
	 */
	public int checkRoleMenu(Common common);
	
	/**
	 * 用户权限管理-角色菜单管理-1.插入角色菜单关系
	 * @param common
	 */
	public void insertRoleMenu(Common common);
	
	/**
	 * 用户权限管理-角色菜单管理-1.删除(更新valid_flag=1)角色菜单关系
	 * @param common
	 * @return
	 */
	public Integer deleteRoleMenu(Common common);
	
	/**
	 * 用户权限管理-角色操作管理-1.查询角色是否已经配有权限
	 * @param map
	 * @return
	 */
	public Integer checkRoleOpList(Map<String, Object> map);
	
	/**
	 * 用户权限管理-角色操作管理-2.插入角色操作权限
	 * @param map
	 */
	public void insertRoleOp(Map<String, Object> map);
	
	/**
	 * 用户权限管理-角色操作管理-删除角色权限关系
	 * @param common
	 * @return
	 */
	public Integer deleteRoleOp(Common common);
	
	/**
	 * 用户登录校验--获取一个用户所有的权限list，存到session中
	 * @param common
	 * @return
	 */
	public List<Management> getUserAllOpList(Common common);
	
	/**
	 * 获取拥有某权限的用户信息
	 * @param map
	 * @return
	 */
	public List<Management> getUserOpList(Map<String, Object> map);

	public List<Management> getUserOpsList(Map<String, Object> map);
	
//	/**
//	 * 获取基金信息
//	 */
//	List<Management> getFundList();
//	
//	/**
//	 * 获得产品授权信息
//	 */
//	List<Management> getUserProductList(Map<String, Object> map);
//	
//	/**
//	 * 查询用户是否存在用户产品关系表中
//	 */
//	Integer checkUserProduct(Map<String, Object> map);
//	
//	/**
//	 * 添加新用户产品权限
//	 */
//	Integer insertUserProduct(Management management);
//	
//	/**
//	 * 添加新用户产品权限
//	 */
//	Integer insertUserProduct2(Management management);
//	
//	/**
//	 * 根据角色删除用户产品关系表，valid_flag设置为1
//	 */
//	Integer deleteUserProductByRole(Management management);
//	
//	/**
//	 * 更新用户产品权限
//	 */
//	Integer updateUserProduct(Map<String, Object> map);
//	
//	/**
//	 * 获取尚未有用户管理的产品列表
//	 */
//	List<Management> getNoUserProduct(Map<String, Object> map);
//	
//	/**
//	 * 查询授权表中是否有已经确定的授权
//	 */
//	Integer checkHisGrant(Management management);
//	
//	/**
//	 * 添加新授权关系
//	 */
//	Integer insertNewGrant(Management management);
//	
//	/**
//	 * 获取sequence
//	 */
//	String getGrantSeq();
//	
//	/**
//	 * 获取被授权列表
//	 */
//	List<Management> getGrantByList(Map<String, Object> map);
//	
//	/**
//	 * 更新授权信息
//	 */
//	Integer updateGrant(Map<String, Object> map);
//	
//	/**
//	 *取消用户产品授权
//	 */
//	Integer deleteGrant(Map<String, Object> map);
//	
//	
//	/**
//	 * 根据Productflag获得操作ID
//	 */
//	List<Management> getOperateIdByProductflag(Map<String, Object> map);
	
}
