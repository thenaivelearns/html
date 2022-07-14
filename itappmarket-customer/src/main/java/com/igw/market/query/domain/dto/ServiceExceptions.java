package com.igw.market.query.domain.dto;

public enum ServiceExceptions {
    SSSS(0, "处理成功"),
    F999(999, "系统未知异常"),
    S001(101, "调用异常相关"),
    S002(102, "参数传递异常相关"),
    S003(103, "数据错误相关"),
    S004(104, "异步执行异常"),
    S005(105, "解析请求失败"),
    F001(201, "具体系统异常相关");

    private Integer errCode;
    private String errMsg;

    private ServiceExceptions(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }


}
