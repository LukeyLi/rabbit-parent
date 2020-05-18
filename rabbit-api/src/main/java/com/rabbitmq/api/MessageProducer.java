package com.rabbitmq.api;


import com.rabbitmq.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 17:11
 **/
public interface MessageProducer {

    void send(Message message, SendCallback sendCallback ) throws MessageRunTimeException;

    void send(Message message) throws MessageRunTimeException;

    void send(List<Message> messages) throws MessageRunTimeException;
}
