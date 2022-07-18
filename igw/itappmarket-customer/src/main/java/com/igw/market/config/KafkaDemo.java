package com.igw.market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaDemo {
    @Autowired
    private Producer producer;
    @RequestMapping("/kafka")
    public String testKafka(String str) {
        producer.sendTest(str);
        return "ok";
    }
}