package com.pansoft.rocketmqserver.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.starter.RocketMQProperties;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName RocketmqConfig
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 20:02
 * @Version 1.0
 **/
@Configuration
@Slf4j
public class RocketmqConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup(rocketMQProperties.getProducer().getGroup());
        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        rocketMQTemplate.setProducer(producer);

        rocketMQTemplate.setMessageQueueSelector((mqs, msg, queNum) -> {
            int queueNum = Integer.parseInt(queNum.toString());
            return mqs.get(queueNum);
        });

        return rocketMQTemplate;
    }
}
