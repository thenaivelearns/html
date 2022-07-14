package com.igw.market.common.conf;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet配置类。包含Listener、Filter、Servlet。
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2019/12/25
 * @since 1.0.0
 */
@Configuration
@ServletComponentScan(basePackages = ServletConfig.BASE_PACKAGES)
public class ServletConfig {

    public static final String BASE_PACKAGES = "com.igw.**.servlet";

}
