package com.cr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
//开启swagger2
@EnableSwagger2
public class SwaggerConfig {

    //配置多个分组
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }
    //配置swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){
        //获取项目的环境
        Profiles profiles = Profiles.of("dev","test");
        //通过flag属性来实现在生产环境部署swagger，发布环境不使用
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                //指定要扫面的包
                .apis(RequestHandlerSelectors.basePackage("com.cr.controller"))
                //path() 过滤路径
                .build();
    }

    //配置swagger信息：apiinfo
    private ApiInfo apiInfo(){
        Contact contact = new Contact("chen ran",
                "http://chenran_uestc.com",
                "chenran_uestc@163.com");
        return new ApiInfo("陈然的swaggerAPI文档",
                "最怕你一生碌碌无为，还安慰自己平凡可贵",
                "版本：1.0",
                "http://localhost:8080",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
