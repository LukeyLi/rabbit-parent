package com.rabbitmq.producer.broker;

import com.rabbitmq.api.Message;
import com.rabbitmq.api.MessageType;
import com.rabbitmq.producer.constant.BrokerMessageConst;
import com.rabbitmq.producer.constant.BrokerMessageStatus;
import com.rabbitmq.producer.entity.BrokerMessage;
import com.rabbitmq.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 20:21
 **/
@Slf4j
public class RabbitBrokerImpl implements RabbitBroker {
    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        // 数据库消息发送日志记录
        Date now = new Date();
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(message.getMessageId());
        brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
        brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
        brokerMessage.setCreateTime(now);
        brokerMessage.setUpdateTime(now);
        brokerMessage.setMessage(message);
        messageStoreService.insert(brokerMessage);
        //执行发送消息
        sendKernel(message);
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
