package com.cr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @desc: 查询所有用户
     * @author:
     * @date:
     * @param:
     * @return:
     **/
    @GetMapping("/users")
    public List<Map<String,Object>> getAllUsers(){
        String sql = "select * from user";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        return mapList;
    }
    /**
     * @desc: 
     * @author: 添加
     * @date:  
     * @param:  
     * @return:  
     **/
    @RequestMapping("/addUser")
    public String addUser(){
        String sql = "insert into user values(9526,'杨英','9989@163.com',18)";
        jdbcTemplate.update(sql);
        return "add-ok";
    }
    @RequestMapping("modifyUser/{id}")
    public String updateUser(@PathVariable("id") Integer id){
        String sql = "update user set name=?, email=? where id="+id;
        //封装到Object对象中
        Object[] objects = new Object[2];
        objects[0] = "修改后的名字";
        objects[1] = "new_email@163.com";
        jdbcTemplate.update(sql,objects);
        return "modify-ok";
    }
    @RequestMapping("deleteUser/{id}")
    public String delUser(@PathVariable("id") Integer id){
        String sql = "delete from user where id="+id;
        jdbcTemplate.update(sql);
        return "delete-ok";
    }
    @GetMapping("/test")
    public String test(){
        return "success";
    }

}
