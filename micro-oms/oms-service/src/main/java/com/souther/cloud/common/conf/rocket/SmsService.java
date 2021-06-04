package com.souther.cloud.common.conf.rocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/6/4 10:55
 */
@Slf4j
@Service
//consumerGroup-消费者组名  topic-要消费的主题
@RocketMQMessageListener(
        consumerGroup = "shop-user", //消费者组名
        topic = "order-topic",//消费主题
        consumeMode = ConsumeMode.CONCURRENTLY,//消费模式,指定是否顺序消费 CONCURRENTLY(同步,默认) ORDERLY(顺序)
        messageModel = MessageModel.CLUSTERING//消息模式 BROADCASTING(广播)  CLUSTERING(集群,默认)
)
public class SmsService implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object o) {
        log.info("收到一个{}消息", JSON.toJSONString(o));
    }
}
