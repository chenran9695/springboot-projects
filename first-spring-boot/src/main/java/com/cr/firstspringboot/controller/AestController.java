package com.cr.firstspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/aest")
public class AestController {
    @ResponseBody
    @RequestMapping("/hello")
    String hello(@RequestParam(value = "c") String c) {
        return "Hello, "+c;
    }

}
