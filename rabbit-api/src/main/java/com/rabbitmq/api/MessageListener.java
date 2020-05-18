package com.rabbitmq.api;

/**
 * @description: 消费者监听消息
 * @author: lzy
 * @create: 2020-05-18 17:37
 **/
public interface MessageListener {

    void onMessage(Message message);
}
