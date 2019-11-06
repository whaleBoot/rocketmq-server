package com.pansoft.rocketmqserver.services.rocketmq;

import com.pansoft.rocketmqserver.support.OperationResult;

/**
 * @ClassName RocketMQService
 * @Description 测试消息队列接口
 * @Author whale
 * @Data 2019/11/5 9:51
 * @Version 1.0
 **/

public interface RocketMQService {

    /**
     * 发送消息
     * @return
     */
    OperationResult<Boolean> sendMessage();
}
