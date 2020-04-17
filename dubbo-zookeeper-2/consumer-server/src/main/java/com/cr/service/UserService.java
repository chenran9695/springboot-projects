package com.cr.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service//放到spring容器中
public class UserService {

    @Reference//引用，方式：1.pom坐标；2.可以定义路径相同的接口名
    TicketService ticketService;

    public void buyTicket(){
        String ticket = ticketService.getTicket();
        System.out.println("在注册中心拿到=>"+ticket);
    }
}
