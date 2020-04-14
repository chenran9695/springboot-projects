package com.cr;

import com.cr.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    void testPojo() throws JsonProcessingException {
        //真是的开发一般都使用json来传递对象
        User user = new User("陈然", 3);
        //使用ObjectMapper进行序列化
        String stringUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", stringUser);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
    @Test
    void testPojo_2() throws JsonProcessingException {
        //真是的开发一般都使用json来传递对象
        User user = new User("陈然", 3);
        //使用ObjectMapper进行序列化
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));

    }
}
