package com.igw.market.common.template;

/**
 * Quartz内存式简易Job接口类。
 * 可以看作一个简单的Job模板。规范了Job的定义必需包含：cron表达式、executeMethodName两个要素。
 * 不进行持久化，重启即丢失。
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2019/12/23
 * @since 1.0.0
 */
public interface QuartzRamJob {

    /**
     * 获取控制Job何时运行的cron表达式。
     *
     * @return String Cron表达式
     */
    public String getCronExpression();


    /**
     * 获取Job的执行方法。
     *
     * @return String Job的执行方法名
     */
    public String getExecuteMethodName();

}
