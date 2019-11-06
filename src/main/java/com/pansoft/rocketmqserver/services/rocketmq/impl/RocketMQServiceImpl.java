package com.pansoft.rocketmqserver.services.rocketmq.impl;


import com.pansoft.rocketmqserver.services.rocketmq.RocketMQService;
import com.pansoft.rocketmqserver.support.OperationResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName RocketMQServiceImpl
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 9:51
 * @Version 1.0
 **/
@Service
@Slf4j
public class RocketMQServiceImpl implements RocketMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value(value = "${whale.producer.topic}")
    private String topic;


    @Override
    public OperationResult<Boolean> sendMessage() {


        SendResult sendResult = null;

        rocketMQTemplate.asyncSendOrderly(topic, "Hello World！", "2", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

                log.info("【onSuccess】{}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.info("【onException】{}", e);
            }
        });

//        rocketMQTemplate.syncSendOrderly()
//        rocketMQTemplate.convertAndSend("demo-topic-1", "Hello, World!");
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("123").build());
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
//        rocketMQTemplate.convertAndSend("test-topic-2", new StudentDTO("joo",2));


        return new OperationResult<>(true);
    }
}
