package com.igw.market.push.dto;


public class UserOperationDTO {
	
	private String name;
	
	// 总人数
	private String personCount;
	
	// 总次数
    private String sumCount;
    
    // 最高次数
    private String maxCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getSumCount() {
		return sumCount;
	}

	public void setSumCount(String sumCount) {
		this.sumCount = sumCount;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}
    
    
	
}
