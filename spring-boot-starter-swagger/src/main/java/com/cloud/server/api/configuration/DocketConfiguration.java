package com.cloud.server.api.configuration;

import com.cloud.server.api.properties.SwaggerProperties;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: DocketConfiguration
 * @description: docket配置启动
 * @author: sw
 * @date: 2021/8/30
 **/
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class DocketConfiguration  implements BeanFactoryAware {
    private BeanFactory beanFactory;
    @Autowired
    private SwaggerProperties swaggerProperties;

    private static final String BEAN_NAME = "spring-boot-starter-swagger-";
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    /**
     * 创建docketBeanFactory里用的api类
     */
    @Bean
    public void createSpringFoxRestApi(){
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)beanFactory;

        // 没有分组
        if (swaggerProperties.getDocket().size() == 0) {
            String beanName = BEAN_NAME + "default";
            BeanDefinition beanDefinition4Group = new GenericBeanDefinition();
            beanDefinition4Group.getConstructorArgumentValues().addIndexedArgumentValue(0, DocumentationType.OAS_30);
            beanDefinition4Group.setBeanClassName(Docket.class.getName());
            beanDefinition4Group.setRole(BeanDefinition.ROLE_SUPPORT);
            beanRegistry.registerBeanDefinition(beanName, beanDefinition4Group);

            Docket docket4Group = (Docket)beanFactory.getBean(beanName);
            ApiInfo apiInfo = apiInfo(swaggerProperties);
            docket4Group.host(swaggerProperties.getHost()).apiInfo(apiInfo).select()
                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                    .paths(paths(swaggerProperties.getBasePath(), swaggerProperties.getExcludePath())).build();
            return;
        }
        /**
         * 解析配置文件
         */
        for (Map.Entry<String, SwaggerProperties.DocketInfo> entry : swaggerProperties.getDocket().entrySet()) {
            String groupName = entry.getKey();
            SwaggerProperties.DocketInfo docketInfo = entry.getValue();
            String beanName = BEAN_NAME + groupName;

            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
                    .description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription()
                            : docketInfo.getDescription())
                    .version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion())
                    .license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense())
                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl()
                            : docketInfo.getLicenseUrl())
                    .contact(new Contact(
                            docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName()
                                    : docketInfo.getContact().getName(),
                            docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl()
                                    : docketInfo.getContact().getUrl(),
                            docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail()
                                    : docketInfo.getContact().getEmail()))
                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty()
                            ? swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
                    .build();

            // base-path处理
            // 当没有配置任何path的时候，解析/**
            if (docketInfo.getBasePath().isEmpty()) {
                docketInfo.getBasePath().add("/**");
            }

            List<Predicate<String>> basePath = new ArrayList<>();
            for (String path : docketInfo.getBasePath()) {
                basePath.add(PathSelectors.ant(path));
            }

            // exclude-path处理
            List<Predicate<String>> excludePath = new ArrayList<>();
            for (String path : docketInfo.getExcludePath()) {
                excludePath.add(PathSelectors.ant(path));
            }
            //请求头
            List<SwaggerProperties.GlobalOperationParameter> globalOperationParameters = docketInfo.getGlobalOperationParameters();
            RequestParameterBuilder requestParameterBuilder=new RequestParameterBuilder();
            List<RequestParameter> parameters = new ArrayList<>();

            for (SwaggerProperties.GlobalOperationParameter s:globalOperationParameters){
                requestParameterBuilder.name(s.getName()).description(s.getDescription())
                        .in(s.getParameterType()).required(s.getRequired());
                parameters.add(requestParameterBuilder.build());

            }
            BeanDefinition beanDefinition4Group = new GenericBeanDefinition();
            beanDefinition4Group.getConstructorArgumentValues().addIndexedArgumentValue(0, DocumentationType.OAS_30);
            beanDefinition4Group.setBeanClassName(Docket.class.getName());
            beanDefinition4Group.setRole(BeanDefinition.ROLE_SUPPORT);
            beanRegistry.registerBeanDefinition(beanName, beanDefinition4Group);

            Docket docket4Group = (Docket)beanFactory.getBean(beanName);
            docket4Group.groupName(groupName).host(docketInfo.getBasePackage()).apiInfo(apiInfo).select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(paths(docketInfo.getBasePath(), docketInfo.getExcludePath()))
                    .build();
            List<Class<?>> ignoredParameterTypes = docketInfo.getIgnoredParameterTypes();
            if(null!=ignoredParameterTypes && ignoredParameterTypes.size()>0){
                int size = ignoredParameterTypes.size();
                Class [] objects=new Class[size];
                for (int i = 0; i < size; i++) {
                    objects[i]=ignoredParameterTypes.get(i);
                }
                docket4Group.ignoredParameterTypes(objects);
            }
            if(parameters.size()>0){
                docket4Group.globalRequestParameters(parameters);
            }
        }
    }
    /**
     * 全局请求参数
     *
     * @param swaggerProperties
     *            {@link SwaggerProperties}
     * @return RequestParameter {@link RequestParameter}
     */
    private List<RequestParameter> globalRequestParameters(SwaggerProperties swaggerProperties) {
        return swaggerProperties.getGlobalOperationParameters().stream()
                .map(param -> new RequestParameterBuilder().name(param.getName()).description(param.getDescription())
                        .in(param.getParameterType()).required(param.getRequired())
                        .query(q -> q.defaultValue(param.getModelRef()))
                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).build())
                .collect(Collectors.toList());
    }

    /**
     * API接口路径选择
     *
     * @param basePath
     *            basePath
     * @param excludePath
     *            excludePath
     * @return path
     */

    private java.util.function.Predicate paths(List<String> basePath, List<String> excludePath) {
        // base-path处理
        // 当没有配置任何path的时候，解析/**
        if (basePath.isEmpty()) {
            basePath.add("/**");
        }
        List<com.google.common.base.Predicate<String>> basePathList = new ArrayList<>();
        for (String path : basePath) {
            basePathList.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<com.google.common.base.Predicate<String>> excludePathList = new ArrayList<>();
        for (String path : excludePath) {
            excludePathList.add(PathSelectors.ant(path));
        }

        return Predicates.and(Predicates.not(Predicates.or(excludePathList)), Predicates.or(basePathList));
    }

    /**
     * API文档基本信息
     *
     * @param swaggerProperties
     *            swaggerProperties
     * @return apiInfo
     */
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder().title(swaggerProperties.getTitle()).description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion()).license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).build();
    }
}
