package com.igw.market.common.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.igw.base.common.component.IgwClassScanner;
import com.igw.base.common.constant.SystemConstant;
import com.igw.market.common.interecptor.AuthenticationInterceptor;
import com.igw.market.common.template.IgwInterceptorTemplate;


/**
 * Web MVC 扩展配置类。基础配置在启动类已自动完成。
 *
 */
@Configuration
public class WebMvcExtensionNewConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = Logger.getLogger(WebMvcExtensionNewConfig.class);

    /**
     * 若Interceptor放置路径的命名规则改变，请配置正确的Interceptor类文件存放路径。
     */
    private static final String BASE_PACKAGE_OF_INTERCEPTOR = "com.igw."
            + System.getProperty(SystemConstant.SYSTEM_PROPERTY_CODE_NAMESPACE) + ".interceptor";

    @Autowired
    private IgwClassScanner igwClassScanner;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    @Value("${spring.resources.static-locations}")
    private String staticLocations;

    
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * 添加ResourceHandlers
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info("--------------- addResourceHandlers start -----------------------.");
        LOGGER.info("staticPathPattern : " + staticPathPattern);
        LOGGER.info("staticLocations : " + staticLocations);
        registry.addResourceHandler(staticPathPattern.split("" + SystemConstant.CHAR_COMMA))
                .addResourceLocations(staticLocations.split("" + SystemConstant.CHAR_COMMA));
        LOGGER.info("--------------- addResourceHandlers end -----------------------.");
    }


    /**
     * 添加Interceptor
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("--------------- addInterceptors start -----------------------.");
        LOGGER.info("Scan package path : " + BASE_PACKAGE_OF_INTERCEPTOR);
        List<HandlerInterceptor> interceptorList = scanInterceptorsInBasePackage();
        LOGGER.info("interceptorList : ");
        if (interceptorList != null) {
            for (HandlerInterceptor interceptor : interceptorList) {

                String pathPatterns = ((IgwInterceptorTemplate) interceptor).getPathPatterns();
                String excludePathPatterns = ((IgwInterceptorTemplate) interceptor).getExcludePathPatterns();
                LOGGER.info(interceptor.getClass().getName() + " [pathPatterns=" + pathPatterns
                        + ", excludePathPatterns=" + excludePathPatterns + "]");
                registry.addInterceptor(interceptor)
                        .addPathPatterns(pathPatterns.split("" + SystemConstant.CHAR_COMMA))
                        .excludePathPatterns(excludePathPatterns.split("" + SystemConstant.CHAR_COMMA));
            }
        }

        String pathPattern = authenticationInterceptor.PATH_PATTERNS;
        String excludePathPattern = authenticationInterceptor.EXCLUDE_PATTERNS;
        registry.addInterceptor(authenticationInterceptor)
        .addPathPatterns(pathPattern.split("" + SystemConstant.CHAR_COMMA))
        .excludePathPatterns(excludePathPattern.split("" + SystemConstant.CHAR_COMMA));
        LOGGER.info("--------------- addInterceptors end -----------------------.");
    }


    private List<HandlerInterceptor> scanInterceptorsInBasePackage() {
        List<HandlerInterceptor> interceptorList = new ArrayList<>();
        Set<Class<?>> classSet = igwClassScanner.scanClassInPackage(BASE_PACKAGE_OF_INTERCEPTOR, true);
        if (classSet != null && classSet.size() > 0) {
            for (Class cls : classSet) {
                //若该类实现了QuartzRamJob接口，则为Job类，实例化到Job列表.
                if (HandlerInterceptor.class.isAssignableFrom(cls)
                        && !HandlerInterceptor.class.equals(cls)
                        && IgwInterceptorTemplate.class.isAssignableFrom(cls)
                        && !IgwInterceptorTemplate.class.equals(cls)) {
                    try {
                        HandlerInterceptor interceptor = (HandlerInterceptor) cls.newInstance();
                        interceptorList.add(interceptor);
                    } catch (Exception e) {
                        LOGGER.error("newInstance for cls : " + cls + " cause an exception : ", e);
                    }
                }
            }
        }
        return interceptorList;
    }

}
