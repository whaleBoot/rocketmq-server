package com.pansoft.rocketmqserver.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName PullMessageSchedulerTask
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 16:00
 * @Version 1.0
 **/
@Component
@Slf4j
public class PullMessageSchedulerTask {

    @Autowired
    private PullConsumer pullConsumer;

    private int count = 0;

    @Scheduled(cron = "*/1 * * * * ?")
    private void process() {
        try {
            List<MessageExt> messageExtList = pullConsumer.pullMessage();
            log.info("===========================");
            for (MessageExt m : messageExtList) {
                log.info(new String(m.getBody()));
            }
            log.info("===========================");

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("this is scheduler task runing  " + (count++));
    }
}
