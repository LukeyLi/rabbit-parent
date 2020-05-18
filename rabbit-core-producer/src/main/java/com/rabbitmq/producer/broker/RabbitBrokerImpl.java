package com.rabbitmq.producer.broker;

import com.rabbitmq.api.Message;
import com.rabbitmq.api.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 20:21
 **/
@Slf4j
public class RabbitBrokerImpl implements RabbitBroker {
    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {

    }

    @Override
    public void reliantSend(Message message) {

    }

    /**
     * 发送消息的核心方法
     * @param message
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit((Runnable)()->{
        CorrelationData correlationData = new CorrelationData(String.format("%s#%s",
                message.getMessageId(),
                System.currentTimeMillis()));
        String topic = message.getTopic();
        String routingKey = message.getRoutingKey();
        RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
        rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
        log.info("#RabbitBrokerImpl.sendKernel#, messageId:{}", message.getMessageId());
        });
    }

}
