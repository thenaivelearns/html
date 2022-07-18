package com.igw.market.systemInfo.domain;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Common implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * pk
	 */
	private String pkSerial;

	/**
	 * 流水号
	 */
	private String sequence;

	private String accountId;
	private String passwordNo;
	private String systemId;
	private String busiType;
	private String newPasswordNo;
	private String mark;
	private String userName;
	private String fundCode;
	private String fundName;
	private String accountName;
	private String taskId;
	private String isLocked;
	private String taskName;
	private String updatedDate;

	private String fileSaveCode;
	private String filePath;
	private long fileSize;
	private String fileType;
	private String isDate;
	private String fileName;
	private String operateId;
	private String operateName;
	
	private String changeDate;
	// id
	private Long id;
	
	// 账户锁定次数（三次锁定）
	private String locksNum;
	// 账户锁定日期
	private String locksDate;


	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileSaveCode() {
		return fileSaveCode;
	}

	public void setFileSaveCode(String fileSaveCode) {
		this.fileSaveCode = fileSaveCode;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getIsDate() {
		return isDate;
	}

	public void setIsDate(String isDate) {
		this.isDate = isDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * id
	 */
	private String code;

	/**
	 * name
	 */
	private String description;

	/**
	 * 套账号
	 */
	private String tzh;

	/**
	 * 日期
	 */
	private Date workDate;

	/**
	 * 是否交易日（0:交易日 1:休市日）
	 */
	private String workFlag;

	/**
	 * 国家代码
	 */
	private String countryId;

	/**
	 * 国家名称
	 */
	private String countryName;

	/**
	 * 开始日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;

	/**
	 * 结束日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;

	/**
	 * 角色信息pk
	 */
	private String rolePk;

	/**
	 * 角色ID
	 */
	private String roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 菜单信息pk
	 */
	private String menuPk;

	/**
	 * 菜单ID
	 */
	private String menuId;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 菜单级别
	 */
	private String menuLevel;

	/**
	 * 菜单url
	 */
	private String menuUrl;

	/**
	 * 上一级菜单
	 */
	private String menuPre;

	//构造函数
	public Common(){

	}

	public Common(String pkSerial){
		this.pkSerial = pkSerial;
	}

	//get and set
	public String getSequence() {
		return sequence;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPasswordNo() {
		return passwordNo;
	}

	public void setPasswordNo(String passwordNo) {
		this.passwordNo = passwordNo;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getNewPasswordNo() {
		return newPasswordNo;
	}

	public void setNewPasswordNo(String newPasswordNo) {
		this.newPasswordNo = newPasswordNo;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getPkSerial() {
		return pkSerial;
	}

	public void setPkSerial(String pkSerial) {
		this.pkSerial = pkSerial;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRolePk() {
		return rolePk;
	}

	public void setRolePk(String rolePk) {
		this.rolePk = rolePk;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuPk() {
		return menuPk;
	}

	public void setMenuPk(String menuPk) {
		this.menuPk = menuPk;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuPre() {
		return menuPre;
	}

	public void setMenuPre(String menuPre) {
		this.menuPre = menuPre;
	}

	public String getTzh() {
		return tzh;
	}

	public void setTzh(String tzh) {
		this.tzh = tzh;
	}

	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getLocksNum() {
		return locksNum;
	}

	public void setLocksNum(String locksNum) {
		this.locksNum = locksNum;
	}

	public String getLocksDate() {
		return locksDate;
	}

	public void setLocksDate(String locksDate) {
		this.locksDate = locksDate;
	}

	@Override
	public String toString(){
		return "Common [accountId=" + accountId + ", ****** ]";
	}

}