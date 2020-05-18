package com.rabbitmq.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.rabbitmq.api.Message;
import com.rabbitmq.api.MessageType;
import com.rabbitmq.api.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 21:54
 **/
@Component
@Slf4j
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {
    private Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();

        rabbitMap.get(topic);
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if(rabbitTemplate != null) {
            return  rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);
        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        //todo message序列化方式
        // newRabbitTemplate.setMessageConverter();

        String messageType = message.getMessageType();
        //除了迅速消息，其他都要confirm
        if (!MessageType.RAPID.equals(messageType)) {
            newRabbitTemplate.setConfirmCallback(this);
        }
        rabbitMap.putIfAbsent(topic, newRabbitTemplate);

        return rabbitMap.get(topic);
    }

    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String s) {
        //具体的消息应答
        List<String> ids = splitter.splitToList(correlationData.getId());
        String messgeId = ids.get(0);
        long sendTime = Long.parseLong(ids.get(1));
        if (ack) {
            log.info("send message is Ok,confirm messageId: {}, sendTime:{}", messgeId, sendTime);
        } else {
            log.error("send message is failed,confirm messageId: {}, sendTime:{}", messgeId, sendTime);
        }
    }
}
