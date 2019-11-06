package com.pansoft.rocketmqserver.controller.api;

import com.pansoft.rocketmqserver.controller.BaseController;
import com.pansoft.rocketmqserver.rocketmq.PullConsumer;
import com.pansoft.rocketmqserver.services.rocketmq.RocketMQService;
import com.pansoft.rocketmqserver.support.JsonResult;
import com.pansoft.rocketmqserver.support.OperationResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("/rocketmq/producer/")
public class RocketMQProducerController extends BaseController {

    @Autowired
    private RocketMQService rocketMQService;
    @Autowired
    private PullConsumer pullConsumer;


    @RequestMapping("sendMessage")
    private JsonResult sendMessage() {


        OperationResult<Boolean> result = rocketMQService.sendMessage();

        if (result.getSucc()) {
            return success(result.getEntity());
        }

        return error(result.getCode(), result.getMessage());
    }

    @GetMapping("/getMessage")
    private void process() {
        try {
            List<MessageExt> messageExtList = pullConsumer.pullMessage();
            if (messageExtList != null) {
                for (MessageExt m : messageExtList) {
                    log.info(new String(m.getBody()));
                }
            } else {
                log.info("message is null!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
