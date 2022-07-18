package com.igwfmc.customer.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author liusj
 * @version 1.0
 * @description: TODO
 * @date 2022/7/14 17:01
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Docket docket() {
        // 设置显示的swagger环境信息
        Profiles profiles = Profiles.of("dev", "sit", "uat");
        // 判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .groupName("分组名称")  // 配置api文档的分组
            .enable(flag)  // 配置是否开启swagger
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.igwfmc.customer")) //配置扫描路径
            .paths(PathSelectors.any()) // 配置过滤哪些
            .build();
    }
    // api基本信息
    private ApiInfo apiInfo() {
        return new ApiInfo("客服子系统重构版swagger",
            "实习生重构项目",
            "v1.0",
            "#",
            new Contact("liusj", "#", "liusj@igwfmc.com"),  //作者信息
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList());
    }
}
