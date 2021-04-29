package com.souther.cloud.common.conf.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/30 14:57
 */
@Slf4j
@Component
@RabbitListener(queues = "order.cancel")
public class DirectReceiver {

    @RabbitHandler
    public void process(String msg) {
        log.info("********************** 收到消息:{} **********************", msg);
    }

}
