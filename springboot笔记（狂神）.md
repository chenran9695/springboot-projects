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

# 任务专题

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

### cron表达式

#### 结构

corn从左到右（用空格隔开）：秒 分 小时 月份中的日期 月份 星期中的日期 年份

#### 常用语法

　　（1）**0 0 2 1 \* ? \***  表示在每月的1日的凌晨2点调整任务

　　（2）**0 15 10 ? \* MON-FRI**  表示周一到周五每天上午10:15执行作业

　　（3）**0 15 10 ? 6L 2002-2006**  表示2002-2006年的每个月的最后一个星期五上午10:15执行作

　　（4）**0 0 10,14,16 \* \* ?**  每天上午10点，下午2点，4点 

　　（5）**0 0/30 9-17 \* \* ?**  朝九晚五工作时间内每半小时 

　　（6）**0 0 12 ? \* WED**   表示每个星期三中午12点 

　　（7）**0 0 12 \* \* ?**  每天中午12点触发 

　　（8）**0 15 10 ? \* \***   每天上午10:15触发 

　　（9）**0 15 10 \* \* ?**   每天上午10:15触发 

　　（10）**0 15 10 \* \* ? \***   每天上午10:15触发 

　　（11）**0 15 10 \* \* ? 2005**   2005年的每天上午10:15触发 

　　（12）**0 \* 14 \* \* ?**   在每天下午2点到下午2:59期间的每1分钟触发 

　　（13）**0 0/5 14 \* \* ?**   在每天下午2点到下午2:55期间的每5分钟触发 

　　（14）**0 0/5 14,18 \* \* ?**   在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 

　　（15）**0 0-5 14 \* \* ?**   在每天下午2点到下午2:05期间的每1分钟触发 

　　（16）**0 10,44 14 ? 3 WED**   每年三月的星期三的下午2:10和2:44触发 

　　（17）**0 15 10 ? \* MON-FRI**   周一至周五的上午10:15触发 

　　（18）**0 15 10 15 \* ?**   每月15日上午10:15触发 

　　（19）**0 15 10 L \* ?**   每月最后一日的上午10:15触发 

　　（20）**0 15 10 ? \* 6L**   每月的最后一个星期五上午10:15触发 

　　（21）**0 15 10 ? \* 6L 2002-2005**  2002年至2005年的每月的最后一个星期五上午10:15触发 

　　（22）**0 15 10 ? \* 6#3**  每月的第三个星期五上午10:15触发

cron在线表达式生成器：http://cron.qqe2.com/

#### 各字段含义

| 字段                     | 允许值                                 | 允许的特殊字符             |
| ------------------------ | -------------------------------------- | -------------------------- |
| 秒（Seconds）            | 0~59的整数                             | , - * /   四个字符         |
| 分（*Minutes*）          | 0~59的整数                             | , - * /   四个字符         |
| 小时（*Hours*）          | 0~23的整数                             | , - * /   四个字符         |
| 日期（*DayofMonth*）     | 1~31的整数（但是你需要考虑你月的天数） | ,- * ? / L W C   八个字符  |
| 月份（*Month*）          | 1~12的整数或者 JAN-DEC                 | , - * /   四个字符         |
| 星期（*DayofWeek*）      | 1~7的整数或者 SUN-SAT （1=SUN）        | , - * ? / L C #   八个字符 |
| 年(可选，留空)（*Year*） | 1970~2099                              | , - * /   四个字符         |

（1）*：表示匹配该域的任意值。假如在Minutes域使用*, 即表示每分钟都会触发事件。

（2）?：只能用在DayofMonth和DayofWeek两个域。它也匹配域的任意值，但实际不会。因为DayofMonth和DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。

（3）-：表示范围。例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次 

（4）/：表示起始时间开始触发，然后每隔固定时间触发一次。例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次. 

（5）,：表示列出枚举值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。 

（6）L：表示最后，只能出现在DayofWeek和DayofMonth域。如果在DayofWeek域使用5L,意味着在最后的一个星期四触发。 

（7）W:表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份 。

（8）LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。 

（9）#:用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2，表示某月的第二个星期三。

### 用法

```java
@EnableScheduling//开启定时调度功能的注解
@Scheduled//什么时候执行
```

```java
package com.cr.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    //在一个特定的时间执行这个方法
    //cron表达式 计划任务
    //秒 分 时 日 月 周几 年
    @Scheduled(cron = "0 * * * * 0-7")
    public void hello(){
        System.out.println("hello,你被执行了~");
    }
}
```

#### TaskExcutor接口

#### TaskScheduler接口

## 邮件发送

### 配置文件

```properties
spring.mail.username=969532470@qq.com
spring.mail.password=xlaftesnvadjbfdj
spring.mail.host=smtp.qq.com
#qq邮箱需要开启加密验证
spring.mail.properties.smtp.ssl.enable=true
```



### 简单邮件(SimpleMailMessage)

```java
@Test
void contextLoads() {
    //一个简单的邮件
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setSubject("springboot学习");
    mailMessage.setText("这里是正文！请注意查收");
    mailMessage.setTo("chenran_uestc@163.com");
    mailMessage.setFrom("969532470@qq.com");
    mailSender.send(mailMessage);
}
```

### 复杂邮件(MimeMailMessage)

```java
@Test
void contextLoads_1() throws MessagingException {
    //一个复杂的邮件
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    //组装
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

    helper.setSubject("springboot");
    helper.setText("<p style='color:red'>大标题</p>", true);
    //附件
    helper.addAttachment("1.jpg", new File("C:\\Users\\67013\\Pictures\\Saved 			Pictures\\1.jpg"));
    helper.setFrom("969532470@qq.com");
    helper.setTo("chenran_uestc@163.com");
}
```

# Springboot整合Redis

资料文档:公众号《狂神说》

springboot操作数据：spring-data jpa jdbc mongodb redis

SpringData和Springboot齐名

说明：在Spring2.x之后，原来使用的jedi被替换为lettuce

jedis:采用直连，多线程操作不安全。如果想要避免不安全，就要jedis pool连接池！BIO

lettuce:采用netty, 实例可以在多个线程总进行共享，不存在线程不安全的情况。减少线程数量！NIO

## 如何整合

```properties
# SpringBoot所有配置类，都有一个自动配置类 RedisAutoConfiguration
# 自动配置类都会绑定一个properties配置文件 RedisProperties
```

```java
@Bean
@ConditionalOnMissingBean(name = "redisTemplate")
public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory 			redisConnectionFactory) throws UnknownHostException {
    //默认的template没有过多的设置，redis对象都是需要序列化的。
    //两个泛型都是Object的类型，使用需要强制类型转换<String, Object>
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}

@Bean
@ConditionalOnMissingBean
//由于String类型是redis中最常使用的类型，所以单独提出了此bean
public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory 				redisConnectionFactory) throws UnknownHostException {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}
```

## 整合步骤

导入依赖>配置连接>测试

导入依赖

```xml
<!--操作redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

配置连接

```properties
# SpringBoot所有配置类，都有一个自动配置类 RedisAutoConfiguration
# 自动配置类都会绑定一个properties配置文件 RedisProperties
# 配置redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
```

测试连接

```java
package com.cr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class Redis02SpringbootApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        //redisTemplate
        //opsForValue 操作字符串 类似String
        // ...
        //除了基本的操作，我们常用的方法都可以直接通过redisTemplate来操作，比如事务和基本的CRUD
        //redisTemplate.opsForValue();
        //获取连接对象
        //RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        redisTemplate.opsForValue().set("mykey", "hello redis!");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }
}
```



## 补充：序列化

在企业中，所有的pojo类都要序列化（最简单方式：pojo类实现java.io.Serializable接口）

- **序列化：将对象写入到IO流中**
- **反序列化：从IO流中恢复对象**
- **意义：序列化机制允许将实现序列化的Java对象转换位字节序列，这些字节序列可以保存在磁盘上，或通过网络传输，以达到以后恢复成原来的对象。序列化机制使得对象可以脱离程序的运行而独立存在。**
- **使用场景：所有可在网络上传输的对象都必须是可序列化的，**比如RMI（remote method invoke,即远程方法调用），传入的参数或返回的对象都是可序列化的，否则会出错；**所有需要保存到磁盘的java对象都必须是可序列化的。通常建议：程序创建的每个JavaBean类都实现Serializeable接口。**

![image-20200414155847175](C:\Users\67013\AppData\Roaming\Typora\typora-user-images\image-20200414155847175.png)

![image-20200414155912170](C:\Users\67013\AppData\Roaming\Typora\typora-user-images\image-20200414155912170.png)

## 自定义序列化

### jdk序列化

SpringData中默认的序列化方式时jdk序列化，形式如：

```java
User(name=陈然, age=3)
```

### json序列化

形式如：

```java
{"name":"陈然","age":3}
```

### 自定义json序列化

从RedisAutoConfiguration.java第8行可以看出，可以通过自定义一个名为redisTemplate的bean对象来实现定制化Redis配置。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
    ...
```

具体配置代码：

```java
package com.cr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {
    //编写我们自己的redisTemplate
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //配置具体的序列化方式
        //这里可以对不同redis不同类型参数(String,list,hash,set)进行不同的序列化配置
        template.setEnableDefaultSerializer(true);
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setDefaultSerializer(objectJackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
```

## 自定义Redis操作类(RedisUtil.java)

```java
package com.cr.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public final class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     *
     * @param key :redis中的键
     * @param time ：过期时间，单位为秒
     * @return
     */
    public boolean expire(String key, long time){
        try {
            if(time > 0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间（单位为秒）
     * @param key :键，非空
     * @return 时间（单位为秒），返回0表示永久失效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false 不存在
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存中指定键值
     * @param key 可以传递单个或多个值
     */
    public void del(String... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //=======================String操作类
    //代码待完成
}
```

