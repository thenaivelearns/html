package com.igw.market.common.domain;

import java.io.Serializable;

/**
 * 用于返回到页面显示消息用
 */
public class Messages implements Serializable {

	private static final long serialVersionUID = -7120083492205293068L;
	
	//消息代码
	private String msgCode;
	
	//消息描述
	private String msgDesc;
	
	
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

}
