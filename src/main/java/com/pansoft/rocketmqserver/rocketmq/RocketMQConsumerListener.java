package com.pansoft.rocketmqserver.rocketmq;

import com.pansoft.rocketmqserver.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.apache.rocketmq.spring.starter.enums.ConsumeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RocketMQConsumerListener
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 13:58
 * @Version 1.0
 **/

public class RocketMQConsumerListener {


    @Slf4j
    @Service
//    @RocketMQMessageListener(topic = "demo-topic-1", consumeMode = ConsumeMode.ORDERLY, consumerGroup = "my-consumer_test-topic-1")
    public static class MyConsumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            log.info("【onMessage topic = demo-topic-1】{}", message);
        }
    }

    @Slf4j
    @Service
//    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public static class MyConsumer2 implements RocketMQListener<StudentDTO> {
        @Override
        public void onMessage(StudentDTO studentDTO) {

            log.info("【onMessage topic = test-topic-2】{}", studentDTO.toString());
        }
    }

//    @Slf4j
//    @Service
//    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
//    public static class MyConsumer3 implements RocketMQListener<String>{
//
//
//        @Override
//        public void onMessage(String message) {
//
//
//            log.info("【onMessage topic = test-topic-2】{}",message);
//        }
//    }
}
