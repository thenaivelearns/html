package com.igw.market.systemInfo.domain;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

public class Management implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
    /**
     * pk
     */ 
	private String pkSerial;
	
	/**
	 * 流水号
	 */ 
	private String sequence;
	  
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
     * 用户pk
     */ 
	private String userPk;
	
	/**
     * 用户ID
     */ 
	private String userId;
	
	/**
     * 用户名称
     */ 
	private String userName;
	
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
	
	private String portCode;
	private String mark;
	private String dateString;
	private String fundCode;
	private String fundName;
	
	private String begDateString;
	private String endDateString;
	private String isGrant;
	private String grantSeq;
	
	private String grantUser;
	private String grantBy;
	
	private Integer seqNo;
	private String systemId;
	private String operateId;
	private String operateName;
	private String accountName;
	
	private String value;
	private String validFlag;
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	//构造函数
	public Management(){
		
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Management(String pkSerial){
		this.pkSerial = pkSerial;
	}
	
	//get and set
    public java.lang.String getSequence() {
		return sequence;
	}

	public void setSequence(java.lang.String sequence) {
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
	
	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
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
	
	public String getBegDateString() {
		return begDateString;
	}
	public void setBegDateString(String begDateString) {
		this.begDateString = begDateString;
	}
	
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	
	public String getIsGrant() {
		return isGrant;
	}
	public void setIsGrant(String isGrant) {
		this.isGrant = isGrant;
	}
	
	public String getGrantSeq() {
		return grantSeq;
	}
	public void setGrantSeq(String grantSeq) {
		this.grantSeq = grantSeq;
	}
	
	public String getGrantUser() {
		return grantUser;
	}
	public void setGrantUser(String grantUser) {
		this.grantUser = grantUser;
	}
	
	public String getGrantBy() {
		return grantBy;
	}
	public void setGrantBy(String grantBy) {
		this.grantBy = grantBy;
	}
	
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
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

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	
}