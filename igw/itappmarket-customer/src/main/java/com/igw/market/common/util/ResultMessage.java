package com.igw.market.common.util;

import java.io.Serializable;

/**
 * 响应类
 * @author aiyongqiang
 *
 * @param <T>
 */
public class ResultMessage<T> implements Serializable{

	/**
	 * serialVersionUID自动生成
	 */
	private static final long serialVersionUID = -7203083949399598804L;

	private String msgCode;
	
	private T data;
	
	private String msgDesc;

	public ResultMessage(String msgCode, T data, String msgDesc) {
		super();
		this.msgCode = msgCode;
		this.data = data;
		this.msgDesc = msgDesc;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	
	public static ResultMessage<String> ok() {
		return new ResultMessage<String>("Y", "成功");
	}
	
	public static<T> ResultMessage<T> ok(T obj) {
		return new ResultMessage<T>("Y", "成功", obj);
	}
	
	public static<T> ResultMessage<T> fail(String desc) {
		return new ResultMessage<T>("N", desc);
	}
	
	public ResultMessage() {
		
	}
	
	public ResultMessage(String msgCode) {
		this.msgCode = msgCode;
	}
	
	public ResultMessage(String msgCode, String msgDesc) {
		this.msgCode = msgCode;
		this.msgDesc = msgDesc;
	}
	
	public ResultMessage(String msgCode, String msgDesc, T data) {
		this.msgCode = msgCode;
		this.msgDesc = msgDesc;
		this.data = data;
	}
	
	public Boolean isOk() {
		if("Y".equals(this.msgCode)) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public String toString() {
		return "ResultMessage [msgCode=" + msgCode + ", data=" + data + ", msgDesc=" + msgDesc + "]";
	}
}