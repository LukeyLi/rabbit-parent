package com.rabbitmq.producer.broker;

import com.rabbitmq.api.Message;
import com.rabbitmq.api.SendCallback;

/**
 * @description:具体发送不同种类型消息的接口
 * @author: lzy
 * @create: 2020-05-18 20:15
 **/
public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);

    /**
     * 消息的发送附带SendCallback函数
     * 特点：异步发送，有ACK，回调函数
     * @param message
     * @param sendCallback
     */
    void sendCallback(Message message, SendCallback sendCallback);
}
