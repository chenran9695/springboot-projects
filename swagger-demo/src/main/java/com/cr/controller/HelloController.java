package com.cr.controller;

import com.cr.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @ApiOperation("Hello控制类")
    @GetMapping("/hello")
    public String hello(){
        return "Hello, swagger";
    }

    //只要我们的接口中，返回值中存在实体类，就会被swagger扫描
    @PostMapping("/user")
    public User user(){
        return new User();
    }
}
