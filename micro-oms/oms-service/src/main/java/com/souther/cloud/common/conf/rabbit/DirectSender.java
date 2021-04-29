package com.souther.cloud.common.conf.rabbit;

import com.souther.cloud.common.constant.QueueEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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

    public void sendMsg(String msg, int delayTime) {
        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey(), msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                return message;
            }
        });
    }
}
