package com.igwfmc.customer.controller;

import com.igwfmc.customer.service.DemoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liusj
 * @version 1.0
 * @description: 演示
 * @date 2022/7/14 16:58
 */
@Api(tags = "演示模块")
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/testGet")

    public String testGet(){
        return demoService.demo();
    }

    @PostMapping("testPost")
    public String testPost(){
        return demoService.demo();
    }
}
