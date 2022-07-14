package com.igw.market.common.template;

/**
 * 简易的Spring Interceptor接口类。
 * 可以看作一个简单的Interceptor模板。规范了Interceptor的定义必需包含：拦截的路径Pattern列表、不拦截的路径Pattern列表。
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2020/03/17
 * @since 1.0.0
 */
public interface IgwInterceptorTemplate {

    /**
     * 获取该Interceptor需拦截的路径Pattern列表。列表元素直接以英文逗号(,)分隔。
     * 最终拦截列表 = getPathPatterns的路径集合 - getExcludePathPatterns的路径集合。
     *
     * @return
     */
    public String getPathPatterns();

    /**
     * 获取该Interceptor不拦截的路径Pattern列表。列表元素直接以英文逗号(,)分隔。
     * 往往用于排除掉特殊的路径。
     *
     * @return
     */
    public String getExcludePathPatterns();

}
