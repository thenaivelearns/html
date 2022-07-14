package com.igw.market.systemInfo.domain;

public class DictInfo {
	// 基金系统代码
	private String keyName;
	// key名称
	private String disMudule;
	// key备注
	private String keyRemark;
	// 有效标记(0:有效 1:无效)
	private String validFlag;
	// 创建人
	private String createdUser;
	// 创建日期
	private String createdDate;
	// 修改人
	private String updatedUser;
	// 修改日期
	private String updatedDate;
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getDisMudule() {
		return disMudule;
	}
	public void setDisMudule(String disMudule) {
		this.disMudule = disMudule;
	}
	public String getKeyRemark() {
		return keyRemark;
	}
	public void setKeyRemark(String keyRemark) {
		this.keyRemark = keyRemark;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
