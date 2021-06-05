package com.souther.cloud.common.conf.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/6/4 17:03
 */
@Slf4j
@Service
public class RocketProducer {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.group}")
    private String group;

    private DefaultMQProducer producer = null;

    @PostConstruct
    public void initMQProducer() {

        producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServer);
//        producer.setRetryTimesWhenSendFailed(3);

        try {
            producer.start();
        } catch (MQClientException e) {
            log.error("rocketmq初始化异常", e);
        }
    }

    /** 废弃：放出消息类型控制权 **/
    @Deprecated
    public boolean send(String topic, String tags, String content) {
        Message msg = new Message(topic, tags, "", content.getBytes());
        try {
            producer.send(msg);
            return true;
        } catch (Exception e) {
            log.error("rocketmq发送消息异常", e);
        }
        return false;
    }

    @PreDestroy
    public void shutDownProducer() {
        if (producer != null) {
            producer.shutdown();
        }
    }

}
