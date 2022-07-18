//package com.igw.market.common.conf;
//
//import com.igw.base.common.component.IgwBeanService;
//import com.igw.base.common.component.IgwClassScanner;
//import com.igw.base.common.constant.SystemConstant;
//import com.igw.market.common.template.QuartzRamJob;
//import org.apache.log4j.Logger;
//import org.quartz.Trigger;
//import org.quartz.impl.JobDetailImpl;
//import org.quartz.impl.triggers.CronTriggerImpl;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// * Quartz配置类。
// *
// */
//@Configuration
//@EnableAutoConfiguration(exclude = {
//        QuartzAutoConfiguration.class
//})
//public class QuartzConfig {
//
//    private static final Logger LOGGER = Logger.getLogger(QuartzConfig.class);
//
//    /**
//     * 若Job放置路径的命名规则改变，请配置正确的Job类文件存放路径。
//     */
//    private static final String BASE_PACKAGE_OF_JOB = "com.igw."
//            + System.getProperty(SystemConstant.SYSTEM_PROPERTY_CODE_NAMESPACE) + ".job";
//
//    private static final String BEAN_NAME_PREFIX_JOB_DETAIL = "igwJobDetail";
//    private static final String BEAN_NAME_PREFIX_TRIGGER = "igwTrigger";
//    private static final String PROPERTY_NAME_TARGET_OBJECT = "targetObject";
//    private static final String PROPERTY_NAME_TARGET_METHOD = "targetMethod";
//    private static final String PROPERTY_NAME_JOB_DETAIL = "jobDetail";
//    private static final String PROPERTY_NAME_CRON_EXPRESSION = "cronExpression";
//
//    @Value("${igw.quartz.auto-startup}")
//    private boolean autoStartup;
//
//    private int defineIndex = 1;
//
//
//    @Bean(name = "schedulerFactoryBean")
//    public SchedulerFactoryBean getSchedulerFactoryBean(
//            @Qualifier(value = IgwBeanService.DEFAULT_BEAN_NAME) IgwBeanService igwBeanService,
//            @Qualifier(value = IgwClassScanner.DEFAULT_BEAN_NAME) IgwClassScanner igwClassScanner) {
//        LOGGER.info("--------------- Init schedulerFactoryBean start -----------------------.");
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setAutoStartup(autoStartup);
//        LOGGER.info("Scan package path : " + BASE_PACKAGE_OF_JOB);
//        List<QuartzRamJob> jobList = scanJobsInBasePackage(igwClassScanner, igwBeanService);
//        LOGGER.info("jobList : ");
//        if (jobList != null) {
//            int jobListSize = jobList.size();
//            Trigger[] triggerArray = new Trigger[jobListSize];
//            for (int i = 0; i < jobListSize; i++) {
//                QuartzRamJob job = jobList.get(i);
//                triggerArray[i] = buildTrigger(jobList.get(i), igwBeanService);
//                LOGGER.info(job.getClass().getName() + " [cronExpression=" + job.getCronExpression()
//                        + ", executeMethodName=" + job.getExecuteMethodName() + "]");
//            }
//            schedulerFactoryBean.setTriggers(triggerArray);
//        }
//        LOGGER.info("--------------- Init schedulerFactoryBean end -------------------------.");
//        return schedulerFactoryBean;
//    }
//
//
//    /**
//     * 扫描指定包路径中的Job
//     *
//     * @param igwClassScanner 自定义的类扫描器
//     * @param igwBeanService  自定义的Bean注册器
//     * @return 扫描到的Job列表
//     */
//    private List<QuartzRamJob> scanJobsInBasePackage(IgwClassScanner igwClassScanner, IgwBeanService igwBeanService) {
//        List<QuartzRamJob> jobList = new ArrayList<>();
//        Set<Class<?>> classSet = igwClassScanner.scanClassInPackage(BASE_PACKAGE_OF_JOB, true);
//        if (classSet != null && classSet.size() > 0) {
//            for (Class cls : classSet) {
//                //若该类实现了QuartzRamJob接口，则为Job类，实例化到Job列表.
//                if (QuartzRamJob.class.isAssignableFrom(cls) && !QuartzRamJob.class.equals(cls)) {
//                    BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(cls).getBeanDefinition();
//                    String className = cls.getSimpleName();
//                    String jobBeanName = ("" + className.charAt(0)).toLowerCase() + className.substring(1);
//                    QuartzRamJob job = (QuartzRamJob) igwBeanService.registerBean(jobBeanName, cls, definition);
//                    jobList.add(job);
//                }
//            }
//        }
//        return jobList;
//    }
//
//
//    /**
//     * 构建Job的触发器
//     *
//     * @param quartzRamJob   自定义的Job
//     * @param igwBeanService 通用Bean服务组件
//     * @return Trigger 该Job的触发器
//     */
//    private Trigger buildTrigger(QuartzRamJob quartzRamJob, IgwBeanService igwBeanService) {
//        String jobDetailBeanName = BEAN_NAME_PREFIX_JOB_DETAIL + defineIndex;
//        BeanDefinition methodInvokingJobDetailFactoryBeanDefinition = BeanDefinitionBuilder
//                .genericBeanDefinition(MethodInvokingJobDetailFactoryBean.class)
//                .addPropertyValue(PROPERTY_NAME_TARGET_OBJECT, quartzRamJob)
//                .addPropertyValue(PROPERTY_NAME_TARGET_METHOD, quartzRamJob.getExecuteMethodName())
//                .getBeanDefinition();
//        JobDetailImpl jobDetail = igwBeanService.registerBean(jobDetailBeanName, JobDetailImpl.class,
//                methodInvokingJobDetailFactoryBeanDefinition);
//        String triggerBeanName = BEAN_NAME_PREFIX_TRIGGER + defineIndex;
//        defineIndex++;
//        BeanDefinition cronTriggerFactoryBeanDefinition = BeanDefinitionBuilder
//                .genericBeanDefinition(CronTriggerFactoryBean.class)
//                .addPropertyValue(PROPERTY_NAME_JOB_DETAIL, jobDetail)
//                .addPropertyValue(PROPERTY_NAME_CRON_EXPRESSION, quartzRamJob.getCronExpression())
//                .getBeanDefinition();
//        CronTriggerImpl trigger = igwBeanService.registerBean(triggerBeanName, CronTriggerImpl.class,
//                cronTriggerFactoryBeanDefinition);
//        return trigger;
//    }
//
//}
