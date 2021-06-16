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

            //单向消息 【分区顺序消息】  todo 不理解mq架构图，重新梳理
//            Message msg4 = new Message("TopicTest" /* Topic */,
//                    "TagB" /* Tag */,
//                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//            );
//            producer.getProducer().sendOneway(msg4, new MessageQueueSelector() {
//                @Override
//                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                    int id = (int) arg;  //根据订单id选择发送queue   【我们这里不是订单id。。。主要确保同一个事务操作投到同一个队列，就能保证顺序】
//                    int index = id % mqs.size();
//                    return mqs.get(index);
//                }
//            }, i);

        }
        return "ok";
    }
}
