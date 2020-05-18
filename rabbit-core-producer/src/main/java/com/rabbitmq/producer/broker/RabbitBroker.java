package com.rabbitmq.producer.broker;

import com.rabbitmq.api.Message;

/**
 * @description:具体发送不同种类型消息的接口
 * @author: lzy
 * @create: 2020-05-18 20:15
 **/
public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void raliantSend(Message message);

}
