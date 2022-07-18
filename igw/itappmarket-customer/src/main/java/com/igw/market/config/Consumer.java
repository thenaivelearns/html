package com.igw.market.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 
 * ClassName: Consumer 
 * @Description: 消费者
 * @date 2018年4月8日
 */

//@Component
//@Slf4j
public class Consumer {
    private Logger log = Logger.getLogger(Consumer.class);
//     /**
//     * 监听demo,sbKafka主题,有消息就读取
//     * @param message
//     */
//    @KafkaListener(topics = {"demo","ceshikaFka"})
//    public void consumer(String message){
//        log.info("message========   "+message);
//        System.err.println(message);
//    }
}