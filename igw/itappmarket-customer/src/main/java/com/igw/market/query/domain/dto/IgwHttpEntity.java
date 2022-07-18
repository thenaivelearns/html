package com.igw.market.query.domain.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IgwHttpEntity<T> {
    private static final Logger logger = LoggerFactory.getLogger(IgwHttpEntity.class);
    private Integer code;
    private String msg;
    private Boolean success;
    private String serialNo;
    private IgwData<T> data;



    public static <T> IgwHttpEntity<List<T>> buildSuccessResponse(List<T> body) {
        IgwHttpEntity<List<T>> response = new IgwHttpEntity();
        response.setCode(ServiceExceptions.SSSS.getErrCode());
        response.setMsg(ServiceExceptions.SSSS.getErrMsg());
        IgwData<List<T>> igwData = new IgwData();
        igwData.setBody(body);
        response.setData(igwData);
        return response;
    }
    public static <T> IgwHttpEntity<T> buildSuccessResponse(T body) {
        IgwHttpEntity<T> response = new IgwHttpEntity();
        response.setCode(ServiceExceptions.SSSS.getErrCode());
        response.setMsg(ServiceExceptions.SSSS.getErrMsg());
        IgwData<T> igwData = new IgwData();
        igwData.setBody(body);
        response.setData(igwData);
        return response;
    }

    public IgwHttpEntity() {
        this.success = Boolean.TRUE;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public IgwData<T> getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setData(IgwData<T> data) {
        this.data = data;
    }


    public String toString() {
        return "IgwHttpEntity(code=" + this.getCode() + ", msg=" + this.getMsg() + ", success=" + this.getSuccess() + ", serialNo=" + this.getSerialNo() + ", data=" + this.getData() + ")";
    }
}
