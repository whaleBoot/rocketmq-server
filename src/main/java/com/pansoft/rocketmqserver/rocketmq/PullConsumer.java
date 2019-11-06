package com.pansoft.rocketmqserver.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.store.OffsetStore;
import org.apache.rocketmq.client.consumer.store.ReadOffsetType;
import org.apache.rocketmq.client.consumer.store.RemoteBrokerOffsetStore;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.PullResultExt;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.starter.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName PullConsumer
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 16:32
 * @Version 1.0
 **/
@Slf4j
@Service
public class PullConsumer {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Value(value = "${whale.producer.topic}")
    private String topic;


    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    public List<MessageExt> pullMessage() throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(rocketMQProperties.getProducer().getGroup());
        consumer.setNamesrvAddr(rocketMQProperties.getNameServer());
        consumer.start();

        try {
            //设置topic
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic);

            for (MessageQueue mq : mqs) {
                if (mq.getQueueId() == 2) {
                    log.info("【pullMessage】Consume from the queue: {}", mq);
                    long offset = consumer.getOffsetStore().readOffset(mq, ReadOffsetType.READ_FROM_STORE);
                    log.info("【pullMessage】当前offset：{}", offset);
                    PullResultExt pullResult = (PullResultExt) consumer.pullBlockIfNotFound(mq, null, offset, 32);
                    //TODO 业务逻辑
                    consumer.getOffsetStore().updateConsumeOffsetToBroker(mq, pullResult.getNextBeginOffset(), true);
                    log.info("【pullMessage】nextBeginOffset={}", pullResult.getNextBeginOffset());

                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            return pullResult.getMsgFoundList();
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (MQClientException e) {
            log.info("【异常】{}", e);
        } finally {
            consumer.shutdown();
        }
        return null;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }

}
