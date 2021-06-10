package com.souther.cloud.controller;

import com.souther.cloud.common.conf.rabbit.DirectSender;
import com.souther.cloud.common.conf.rocket.RocketProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/14 15:17
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private DirectSender directSender;

    @Resource
    private RocketProducer producer;

    @GetMapping("a")
    public String test() {
        log.info("正在oms-service服务");
        throw new RuntimeException("测试异常");
//        return "oms msg";
    }

    @GetMapping("rabbit")
    public void sendMq(@RequestParam Integer delayTime) {
        directSender.sendMsg("测试消息", delayTime);
    }

    @GetMapping("rocketmq")
    public String sendRocketMq() throws Exception {

        for (int i = 0; i < 100; i++) {
            final int index = i;

            //发同步消息  【其实，同步已经包含顺序顺序消费的意思了】
            // 创建消息，并指定Topic，Tag和消息体
//            Message msg = new Message("TopicTest" /* Topic */,
//                    "TagA" /* Tag */,
//                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//            );
//            SendResult sendResult = producer.getProducer().send(msg);
//            log.info(String.format("同步消息返回信息：%s", sendResult));


            //异步消息
//            Message msg2 = new Message("TopicTest",
//                    "TagA",
//                    "OrderID188",
//                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
//            // SendCallback接收异步返回结果的回调
//            producer.getProducer().send(msg2, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    log.info(String.format("%-10d OK %s %n", index,
//                            sendResult.getMsgId()));
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    log.error(String.format("%-10d Exception %s %n", index, e));
//                }
//            });


            //单向消息
            Message msg3 = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.getProducer().sendOneway(msg3);

        }
        return "ok";
    }
}
