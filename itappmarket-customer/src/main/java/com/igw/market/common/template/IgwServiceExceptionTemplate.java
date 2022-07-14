package com.igw.market.common.template;

import org.springframework.http.HttpStatus;

/**
 * 自定义的Service异常模板。
 * 该接口类定义了Service异常需要包含errorCode、errorMessage、httpStatus，该信息可以直接呈现到前端。
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2020/03/05
 * @since 1.0.0
 */
public interface IgwServiceExceptionTemplate {

    /**
     * 获取异常状态码
     *
     * @return
     */
    public String getErrorCode();

    /**
     * 获取异常提示信息
     *
     * @return
     */
    public String getErrorMessage();

    /**
     * 获取该异常对应的HTTP状态码
     *
     * @return
     */
    public HttpStatus getHttpStatus();

}
