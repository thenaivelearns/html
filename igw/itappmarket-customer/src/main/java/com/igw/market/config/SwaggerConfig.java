package com.igw.market.config;

import org.springframework.context.annotation.Bean;

//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//访问的URL也是个固定的格式:http://ip地址:端口/项目名/swagger-ui.html#/
// springfox-swagger-ui 访问【 http://localhost:8081/swagger-ui.html】
// swagger-bootstrap-ui 访问【 http://localhost:8081/doc.html】

@Configuration // swagger2启动注解
@EnableSwagger2 // 声明这是一个配置类
// @ComponentScan(basePackages = {"com.igw.demo.controller"}) //
// 指定需要生成API文档的类所在的包路径
public class SwaggerConfig {

	/**
	 * createRestApi方法不需要更改，主要用于swagger的初始化设置，
	 * 包括扫描API注解路径等，用我提供的createRestApi默认扫描当前项目全部路径， 这里的扫描与上面的@ComponentScan不同，
	 * 这里扫描的不会显示在swagger-ui（swaggerAPI文档可视化界面，最后会说）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public Docket createRestApi() throws Exception {
		return new Docket(DocumentationType.SWAGGER_2).groupName("adminApi").apiInfo(apiInfo()).pathMapping("/") // 最终调用接口后会和paths拼接在一起
				.select().apis(RequestHandlerSelectors.basePackage("com.igw.market"))
				.apis(RequestHandlerSelectors.any()) // 选择那些路径和api会生成document
				.paths(PathSelectors.any()) // 对所有路径进行监控
				.build();
	}

	public ApiInfo apiInfo() throws Exception {
		return new ApiInfoBuilder()
				// 标题
				.title("《内网系统》")
				// 描述
				.description(
						"内网系统，整合目前内网，制作统一的发布平台，便于内网的日常推广使用和收集整理，以及材料的数据分析服务，也为后续设计、编码、测试、发布等系统开发工作的指导性文档.\r\n"
								+ "")
				.version("1.0.1")// 版本号
				.contact(new Contact("景顺长城", "http://www.igwfmc.com/main/index/index.html", "fuzw@igwfmc.com"))// 作者信息
				.build();
	}

}