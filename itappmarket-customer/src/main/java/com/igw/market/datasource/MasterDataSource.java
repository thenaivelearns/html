package com.igw.market.datasource;

import com.igw.base.dynamicdatasource.template.IgwDynamicDataSourceDefineTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * 示例数据源
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2020/04/03
 * @since 1.0.0
 */
public class MasterDataSource implements IgwDynamicDataSourceDefineTemplate {

    /**
     * 数据源驱动名称
     */
    @Value("${spring.datasource.master.driver-class-name}")
    private String driverClassName;
    /**
     * 数据源连接地址
     */
    @Value("${spring.datasource.master.jdbc-url}")
    private String jdbcUrl;
    /**
     * 数据源用户名
     */
    @Value("${spring.datasource.master.username}")
    private String username;
    /**
     * 数据源密码
     */
    @Value("${spring.datasource.master.password}")
    private String password;
    /**
     * 是否默认数据源
     */
    @Value("${spring.datasource.master.default-datasource}")
    private boolean defaultDataSource;


    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isDefaultDataSource() {
        return defaultDataSource;
    }

}
