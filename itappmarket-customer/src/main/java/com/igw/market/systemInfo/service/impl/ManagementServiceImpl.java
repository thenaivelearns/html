package com.igw.market.systemInfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.igw.base.common.annotation.CommonLog;
import com.igw.base.dynamicdatasource.annotation.DBType;
import com.igw.base.dynamicdatasource.conf.DynamicDataSourceConfig;
import com.igw.market.systemInfo.dao.ManagementDao;
import com.igw.market.systemInfo.domain.Common;
import com.igw.market.systemInfo.domain.Management;
import com.igw.market.systemInfo.service.ManagementService;

@Service("managementService")
public class ManagementServiceImpl  implements ManagementService {

	@Autowired
	private ManagementDao managementDao;
	
	@Value("#{configProperties}")
	private Properties properties;
	
	@Override
	@CommonLog
	public List<Management> getUserList(Map<String, Object> map) {
		// 用户管理-用户信息list查询 
		return managementDao.getUserList(map);
	}
	
	@Override
	@CommonLog
	public List<Management> getRoleList(String systemId) {
		// 角色管理-角色信息list查询
		return managementDao.getRoleList(systemId);
	}
	
	@Override
	@CommonLog
	public List<Management> getMenuList(String systemId) {
		// 菜单管理-菜单信息list查询
		return managementDao.getMenuList(systemId);
	}
	
	@Override
	@CommonLog
	public List<Management> getOpList(String systemId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemId", systemId);
		// 操作管理-查询操作权限 list查询
		return managementDao.getOpList(map);
	}
	
	@Override
	@CommonLog
	public Integer addNewUser(Common common){
		// 查询此用户是否存在
		if(managementDao.checkUserName(common)==0){
			// 用户管理-添加新用户
			managementDao.insertNewUser(common);
			return 0;
		}
		return 1;
	}
	
	@Override
	@CommonLog
	public Integer resetPassword(Common common){
		// 用户管理-重置密码
		return managementDao.resetPassword(common);
	}
	
	@Override
	@CommonLog
	public Integer deleteUser(Common common){
		// 用户管理-删除(更新valid_flag=1)用户
		return managementDao.deleteUser(common);
	}
	
	@Override
	@CommonLog
	public Integer regainUser(Common common) {
		// 查询此用户是否存在
	   if(managementDao.checkUserName(common)==0){
		   // 用户管理-恢复(更新valid_flag=0)用户
		   return managementDao.regainUser(common);
	    }
	   return 2;
	}
	
	@Override
	@CommonLog
	public Integer addNewRole(Common common){
		// 查询此用户是否存在
		if(managementDao.checkRoleId(common)==0){
			// 角色管理-添加新角色
			managementDao.insertNewRole(common);
			return 0;
		}
		return 1;
	}
	
	@Override
	@CommonLog
	public Integer deleteRole(Common common){
		// 角色管理-删除角色
		return managementDao.deleteRole(common);
	}
	
	@Override
	@CommonLog
	public Integer addNewMenu(Common common){
		// 查询此用户是否存在
		if(managementDao.checkMenuId(common)==0){
			// 菜单管理-添加新菜单
			managementDao.insertNewMenu(common);
			return 0;
		}
		return 1;
	}
	
	@Override
	@CommonLog
	public Integer deleteMenu(Common common){
		// 菜单管理-删除菜单
		return managementDao.deleteMenu(common);
	}
	
	@Override
	@CommonLog
	public Integer addNewOp(Common common){
		// 查询此用户是否存在
		if(managementDao.checkOpId(common)==0){
			// 操作管理-添加新操作
			managementDao.insertNewOp(common);
			return 0;
		}
		return 1;
	}
	
	@Override
	@CommonLog
	public Integer deleteOp(Common common){
		// 操作管理-删除操作
		return managementDao.deleteOp(common);
	}
	
	@Override
	@CommonLog
	public List<Management> getUserRoleList(String systemId) {
		// 用户角色管理-获取用户对应的角色信息list
		return managementDao.getUserRoleList(systemId);
	}
	
	@Override
	@CommonLog
	public List<Management> getRoleMenuList(String systemId) {
		// 角色菜单管理-获取角色对应的菜单list
		return managementDao.getRoleMenuList(systemId);
	}
	
	@Override
	@CommonLog
	public List<Management> getOpRoleList(String systemId){
        // 角色操作管理-获取角色操作list
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemId", systemId);
		List<Management> list = managementDao.getOpRoleList(map);
		Management management = new Management();
		List<Management> resultList = new ArrayList<Management>();
		
		for(Management dto:list){
			
			String roleId = dto.getRoleId();
			String operateId = dto.getOperateId();
			
			int mark = 0;
			for(Management dto2:resultList){
				if(dto2.getRoleId().equals(roleId)){
					dto2.setOperateId(dto2.getOperateId()+","+operateId);
					mark++;
					break;
				}
			}
			if(mark==0){
				management = new Management();
				management.setOperateId(operateId);
				management.setRoleId(roleId);
				management.setRolePk(dto.getRolePk());
				management.setRoleName(dto.getRoleName());
				management.setPkSerial(dto.getPkSerial());
				resultList.add(management);
			}
		}
		return resultList;
	}
	
	@Override
	@CommonLog
	public Integer checkUserRole(Common common){
		// 用户角色管理-1.查询用户角色是否存在
		return managementDao.checkUserRole(common);
	}

	@Override
	@CommonLog
	public void addNewUserRole(Common common){
		// 用户角色管理-2.插入新用户及其角色关系
		managementDao.insertNewUserRole(common);
	} 
	
	@Override
	@CommonLog
	public Integer deleteUserRole(Common common){
		// 用户角色管理-删除(更新valid_flag=1)用户角色关系
		return managementDao.deleteUserRole(common);
	}
	
	@Override
	@CommonLog
	public Integer checkRoleMenu(Common common){
		// 菜单管理-1.查询角色菜单是否存在
		return managementDao.checkRoleMenu(common);
	}
	
	@Override
	@CommonLog
	public void addRoleMenu(Common common){
		// 菜单管理-2.插入角色菜单关系
		managementDao.insertRoleMenu(common);
	}
	
	@Override
	@CommonLog
	public Integer deleteRoleMenu(Common common){
		// 菜单管理-删除(更新valid_flag=1)角色菜单关系
		return managementDao.deleteRoleMenu(common);
	}
	
	@Override
	@CommonLog
	public Map<String, Object> addRoleOp(Map<String, Object> map){
		// 角色操作管理-添加角色操作权限
		String[] opPks = ((String) map.get("opPks")).split(",");
		if(managementDao.checkRoleOpList(map)==0){
			map.put("msgCode", "Y");
			for(String opPk:opPks){
				map.put("opPk", opPk);
				managementDao.insertRoleOp(map);
			}
		}else{
			map.put("msgCode", "N");
			map.put("msgDesc", "此角色已经配置过操作权限，请直接修改");
		}	
		return map;
	}
	
	@Override
	@CommonLog
	public Integer deleteRoleOp(Common common){
		// 角色操作管理-删除角色操作关系
		return managementDao.deleteRoleOp(common);
	}
	
	@Override
	@CommonLog
	public Map<String, Object> getUserAllOpList(Common dto){
		// 用户登录校验--获取一个用户所有的权限list，存到session中
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("fundList", managementDao.getFundList());
		map.put("opList", managementDao.getUserAllOpList(dto));
		return map;
	}
	
	public List<Management> getUserOpList(Map<String, Object> map) {
		// 获取拥有某权限的用户信息
		return managementDao.getUserOpList(map);
	}

	@Override
	public List<Management> getUserOpsList(Map<String, Object> map) {
		return managementDao.getUserOpsList(map);
	}
	
	
	
//	public List<Management> getFundList(){
//		return managementDao.getFundList();
//	};
//	
//	public Map<String, Object> getUserProductList(Map<String, Object> map){
//		
//		// 查询所有非授权的列表
//		List<Management> list = managementDao.getUserProductList(map);
//		List<Management> resultList = new ArrayList<Management>();
//				
//		Management management = new Management();
//		for(Management dto:list){
//			
//			String userId = dto.getUserId();
//			String fundCode = dto.getFundCode();
//			String fundName = dto.getFundName();
//			
//			int mark = 0;
//			for(Management dto2:resultList){
//				if(dto2.getUserId().equals(userId)){
//					dto2.setFundCode(dto2.getFundCode()+","+fundCode);
//					dto2.setFundName(dto2.getFundName()+","+fundName);
//					mark++;
//					break;
//				}
//			}
//			if(mark==0){
//				management = new Management();
//				management.setUserId(userId);
//				management.setFundCode(fundCode);
//				management.setFundName(fundName);
//				management.setRoleId(dto.getRoleId());
//				management.setOperateId(dto.getOperateId());
//				management.setAccountName(dto.getAccountName());
//				management.setRoleName(dto.getRoleName());
//				resultList.add(management);
//			}
//		}
//		map.put("resultList", resultList);
//		if(!"".equals(map.get("isNoUser")) && null != map.get("isNoUser")){
//			map.put("noUserList", managementDao.getNoUserProduct(map));
//		}
//		return map;
//	}
//
//	public Integer addUserProducts(Management management){
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", management.getUserId());
//		map.put("roleId", management.getRoleId());
//		map.put("systemId", (String)properties.get("project.name"));
//		if(managementDao.checkUserProduct(map)>0){
//			return 1;
//		}else{
//			String[] fundCode = management.getFundCode().split(",");
//			management.setBegDateString(Constant.DATE_FORMAT_8.format(new Date()));
//			management.setEndDateString("29990101");
//			management.setIsGrant("1");
//			management.setGrantSeq("");
//			management.setSystemId((String)properties.get("project.name"));
//			
//			for(String code:fundCode){
//				management.setFundCode(code);
//				managementDao.insertUserProduct(management);
//			}
//			return 0;
//		}
//	}
//	
//	public Integer addUserProducts2(Management management){
//		return managementDao.insertUserProduct2(management);
//	}
//	
//	public Integer deleteUserProductByRole(Management management){
//		return managementDao.deleteUserProductByRole(management);
//	}
//	
//	
//	public List<Management> getOperateIdByProductflag(Map<String, Object> map){
//		return managementDao.getOperateIdByProductflag(map);
//	}

}
