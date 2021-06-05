package com.souther.cloud.controller;

import com.souther.cloud.common.conf.rabbit.DirectSender;
import lombok.extern.slf4j.Slf4j;
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
    public void sendRocketMq() {
        //发同步消息
        //异步消息
        //单向消息
    }
}
