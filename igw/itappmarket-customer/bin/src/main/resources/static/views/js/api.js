// 获取当前
function getSystemCir() {
	  return server.post("/config/getSystemCir",{});
}
//----------AuthenticationController:登录 相关 -------------------------------------start
/**
* 用户登录校验
* @param params
* @returns
*/
function loginToSystem(params) {
  return server.post("/loginToSystem", params);
}

/**
* 登陆进入主菜单
* @returns
*/
function home() {
  return server.post("/home");
}
/**
* 直接跳转到修改密码页面
* @param params
* @returns
*/
function pwdPage() {
  return server.post("/pwdPage");
}
/**
* 修改密码
* @param params
* @returns
*/
function updPassword(params) {
  return server.post("/updPassword", params);
}

/**
 * 查询：获取系统所有用户中文名
 * @returns
 */
function getAllUsers(){
	return server.post("/getAllUsers");
}

/**
* 登出，退回登录页面
* @returns
*/
function logout() {
  return server.post("/logout");
}

//----------AuthenticationController:登录 相关 -------------------------------------end

//----------ManagementController:系统管理-基础信息管理页面 相关 -------------------------start
/**
* 初始化系统【基础信息管理】页面
* @returns
*/
function infomanagement() {
  return server.post("/infomanagement");
}

/**
* 用户管理-重置密码
* @returns
*/
function resetPassword(params) {
  return server.post("/resetPassword",params);
}

/**
* 用户管理-新增用户
* @returns
*/
function insertNewUser(params) {
  return server.post("/insertNewUser",params);
}

/**
* 用户管理-删除用户
* @returns
*/
function deleteUser(params) {
  return server.post("/deleteUser",params);
}

/**
* 用户管理-恢复用户
* @returns
*/
function regainUser(params) {
  return server.post("/regainUser",params);
}

/**
* 角色管理-新增用户
* @returns
*/
function insertNewRole(params) {
  return server.post("/insertNewRole",params);
}

/**
* 角色管理-删除用户
* @returns
*/
function deleteRole(params) {
  return server.post("/deleteRole",params);
}

/**
* 菜单管理-新增菜单
* @returns
*/
function insertMenu(params) {
  return server.post("/insertMenu",params);
}

/**
* 菜单管理-删除菜单
* @returns
*/
function deleteMenu(params) {
  return server.post("/deleteMenu",params);
}

/**
* 操作管理-新增操作权限
* @returns
*/
function insertOp(params) {
  return server.post("/insertOp",params);
}

/**
* 操作管理-删除操作权限
* @returns
*/
function deleteOp(params) {
  return server.post("/deleteOp",params);
}
//----------ManagementController:系统管理-基础信息管理页面 相关 -------------------------end

//----------ManagementController:系统管理-用户权限管理页面 相关 -------------------------start
/*
 * 初始化系统【用户权限管理】页面
 * @returns
 */
function permission() {
  return server.post("/permission");
}

/**
* 用户角色关系管理-新增关系
* @returns
*/
function insertUserRole(params) {
  return server.post("/insertUserRole",params);
}

/**
* 用户角色关系管理-删除关系
* @returns
*/
function deleteUserRole(params) {
  return server.post("/deleteUserRole",params);
}

/**
* 角色菜单关系管理-新增关系
* @returns
*/
function insertRoleMenu(params) {
  return server.post("/insertRoleMenu",params);
}

/**
* 角色菜单关系管理-删除关系
* @returns
*/
function deleteRoleMenu(params) {
  return server.post("/deleteRoleMenu",params);
}

/**
* 角色操作关系管理-新增关系
* @returns
*/
function insertRoleOp(params) {
  return server.post("/insertRoleOp",params);
}

/**
* 角色操作关系管理-删除关系
* @returns
*/
function deleteRoleOp(params) {
  return server.post("/deleteRoleOp",params);
}

/**
 * 查询：获取拥有某权限的用户信息
 * @param params
 * @returns
 */
function getUserOpList(params) {
	  return server.post("/getUserOpList",params);
}
//----------ManagementController:系统管理-用户权限管理页面 相关 -------------------------end
