package com.igw.market;

import com.igw.base.common.conf.SystemPropertiesConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * SpringBoot项目启动类。
 *
 * @author chenzy1
 * <p>
 * create at 2019/11/11
 */
//SpringBoot启动类注解，让spring boot自动给程序进行必要的配置
@SpringBootApplication
//配置扫描包路径
@ComponentScan("com.igw")
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.igw.market.controller"})
public class CustomerApplication {

    private static final Logger LOGGER = Logger.getLogger(CustomerApplication.class);

    //SpringBoot项目启动函数
    public static void main(String[] args) {

        //加载Spring框架等组件之前，先预置SystemProperties系统属性。
        SystemPropertiesConfig.setSystemProperties(CustomerApplication.class);

        SpringApplication.run(CustomerApplication.class, args);
    }

    // 兼容以前的 @Value("#{configProperties}")
    @Bean(name = "configProperties")
    public PropertiesFactoryBean getPropertiesFactoryBean() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:application.properties");
        LOGGER.info("configProperties files:");
        for (Resource resource : resources) {
            LOGGER.info("URL :" + resource.getURL());
        }
        propertiesFactoryBean.setLocations(resources);
        return propertiesFactoryBean;
    }

}