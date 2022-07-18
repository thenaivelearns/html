function delMarket(params) {
	return server.post("/institutionalTrade/delete", params);
}
function addMarket(params) {
	return server.post("/institutionalTrade/add", params);
}
function editMarket(params) {
	return server.post("/institutionalTrade/edit", params);
}
function getMarketList(params) {
	return server.post("/institutionalTrade/list", params);
}
//----------AnnualBillController: 年度账单 相关 -------------------------------------start
function getBillList(params) {
	  return server.post("/annualBill/list", params);
}
function getFundDividendList(params) {
      return server.post("/wx_push/dividend_notice_list/v1", params);
}
//保存分红通知
function saveDividendInfo(params) {
    return server.post("/userFund/update_funddividend", params);
}
//查询分红通知
function getDividendNoticeInfo(params) {
    return server.post("/wx_push/get_dividend_notice_info/v1", params);
}
//基金列表
function selectFundList(params){
    return server.post("/userFund/fundNameList", params);
}
//删除分红通知
function deleteDividend(params){
    return server.post("/userFund/delete_funddividend", params);
}
//用户推送列表
function getUserPushList(params){
    return server.post("/wx_push/user_push_list/v1", params);
}
//测试用户列表
function getTestUser(){
    return server.post("/wx_push/get_test_user/v1", {});
}
//测试用户推送
function userPushTest(params){
    return server.post("/wx_push/user_push_test/v1", params);
}
function getMyBillDetail(params) {
	  return server.post("/annualBill/getMyBill", params);
}
function synchro(params) {
	  return server.post("/annualBill/synchro", params);
}
function synchroMonth(params) {
	return server.post("/annualBill/synchroMonth", params);
}
//公告日历信息
function getAlmanacList(params) {
	  return server.post("/almanac/get_almanac_list", params);
}
function addAlmanac(params) {
	  return server.post("/almanac/add_almanac", params);
}
function editAlmanac(params) {
	  return server.post("/almanac/edit_almanac", params);
}
function deleteAlmanac(params) {
	  return server.post("/almanac/delete_almanac", params);
}
function getNoticeInfoList() {
	  return server.get("/almanac/get_notice");
}
function getFundInfoList() {
	  return server.get("/fundInfo/getFundInfoList");
}
//----------AnnualBillController: 年度账单 相关 -------------------------------------end
//----------SubscriptionStatisticsController: 订阅统计 相关 -------------------------------------start
//  查询当前订阅/退订人数
function getSubscriptionCounts(params) {
	  return server.post("/subscriptionStatistics/getSubscriptionCounts", params);
}
// 历史
function getHistorySubscriptionCounts(params) {
	  return server.post("/subscriptionStatistics/getHistorySubscriptionCounts", params);
}
function getUserOperation(params) {
	  return server.post("/userOperation/getUserOperation", params);
}
//----------SubscriptionStatisticsController: 订阅统计 相关 -------------------------------------end
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
  return server.post("/loginHome");
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