package com.rabbitmq.producer.broker;

import com.google.common.base.Preconditions;
import com.rabbitmq.api.Message;
import com.rabbitmq.api.MessageProducer;
import com.rabbitmq.api.MessageType;
import com.rabbitmq.api.SendCallback;
import com.rabbitmq.api.exception.MessageRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 19:53
 **/
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.raliantSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.raliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }
}
