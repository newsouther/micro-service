package com.souther.cloud.common.conf.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/4/21 10:12
 */
@Component
public class DirectSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", msg);
    }
}
