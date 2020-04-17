package com.cr.service;

//项目一启动就自动注册到服务中心
@org.apache.dubbo.config.annotation.Service
@org.springframework.stereotype.Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "获得一张票：hdskhf1213dj";
    }
}
