# Swagger

## 学习目标

- 了解Swagger的组用和概念
- 了解前后端分离
- 在Springboot中集成Swagger

 

 

## Swagger简介

### 前后端分离

​	Vue+Springboot

### 后端时代

​	前端只用管理静态页面；html==>后端。模板引擎 JSP =>后端是主力

 

### 前后端分离时代

​	前端：前端控制层，视图层

​	伪造后端数据，json，不需要后端，前端依旧可以工作

​	后端：后端是控制层，服务层，数据访问层

​	前后端如何交互？===>API

​	前后端相对独立，松耦合；

​	前后端甚至可以部署在不同的服务器上

 

### 前后端分离产生的问题

前后端集成联调，前后端人员无法做到及时协商，导致问题集中爆发；

 

### 解决方案

制定Schema[计划的提纲]，时时更新最新的API，降低集成的风险；

早些年制定word文档；

前后端分离：

前端测试后端接口：postman(原google浏览器自带)

后端提供接口，需要实时更新最新的消息及改动

 

### Swagger特点

（url: http://swagger.io）

号称世界上最流行的Api框架

RestFul Api 文档在线自动生成工具=>Api文档与Api定义同步更新

直接运行，在线测试API接口

支持多种语言

 

在项目中使用Swagger需要Springbox

​	swagger2

​	ui

 

### Springboot集成Swagger

#### 1.新建springboot-web项目

#### 2.导入相关依赖

```java
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->

<dependency>

  <groupId>io.springfox</groupId>

  <artifactId>springfox-swagger2</artifactId>

  <version>2.9.2</version>

</dependency>
```

 

```java
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->

<dependency>

  <groupId>io.springfox</groupId>

  <artifactId>springfox-swagger-ui</artifactId>

  <version>2.9.2</version>

</dependency>
```

 

#### 3.简单配置swagger

配置swagger的Docket的bean实例

```java
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


    //配置swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
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
```

## swagger配置详解

### swagger仅在生产环境中使用

原理：利用enbale()实现

```java
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
```

### 配置API文档的分组

```java
.groupName("陈然")
```

### 配置多个分组

多个Docket实例即可实现

```java
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
```

### 实体类配置

```java
package com.cr.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("用户实体类")
public class User {
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("用户密码")
    public String passwrd;
}
```

此外，还有@ApiOperation，@ApiParm，@ApiResponse等

## 总结

1.Swagger用于给比较难理解的属性或者接口增加注释信息

2.接口文档实时更新（解决前后端分离带来的问题）

3.可以在线测试

【注意点】在正式发布时，关闭Swagger：出于安全和节约运行内存

# 任务

## 异步任务

```java
package com.cr.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    //告诉spring这是一个异步方法
    @Async
    public void hello(){
        System.out.println("数据正在处理中...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据处理完成");
    }
}
```

```java
package com.cr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync//开启异步注解功能
@SpringBootApplication
public class Springboot09TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot09TestApplication.class, args);
    }

}
```



## 定时任务

## 邮件发送