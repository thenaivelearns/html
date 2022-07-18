package com.igw.market.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
/**
 * 
 * ClassName: Producer 
 * @Description:kafka生产者
 * @date 2018年4月8日
 */
@Component
public class Producer {
       /**
         * 这个类包装了个生产者，来提供方便的发送数据到kafka的topic里面。 
         * 同步和异步的方法都有，异步方法返回一个Future
         */
          @Autowired
        private KafkaTemplate<String,Object> kafkaTemplate;

        /**
         * 发送消息到kafka,主题为sbKafka
         */
        public void sendTest(String str){
            kafkaTemplate.send("ceshikaFka","北京时间: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        }
}