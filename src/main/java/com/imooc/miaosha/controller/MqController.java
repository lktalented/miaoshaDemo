package com.imooc.miaosha.controller;

import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 队列controller
 * luokai
 * 2019/1/15 0015 下午 2:37
 */
@Controller
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq(){
        mqSender.send("hello,mq!");
        return Result.success("hello,mq!");
    }

    @RequestMapping("/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("hello,topic!");
        return Result.success("Hello，topic!");
    }

    @RequestMapping("/fanout")
    @ResponseBody
    public Result<String> fanout() {
        mqSender.sendFanout("hello,fanout!");
        return Result.success("Hello，fanout!");
    }

    @RequestMapping("/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeader("hello,fanout");
        return Result.success("Hello，fanout");
    }

}
