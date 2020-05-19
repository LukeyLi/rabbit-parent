package com.rabbitmq.producer.service;

import com.rabbitmq.producer.constant.BrokerMessageStatus;
import com.rabbitmq.producer.entity.BrokerMessage;
import com.rabbitmq.producer.mapper.BrokerMessageMapper;

import java.util.Date;
import java.util.List;

public class MessageStoreService {

    private BrokerMessageMapper brokerMessageMapper;

    public MessageStoreService(BrokerMessageMapper brokerMessageMapper) {
        this.brokerMessageMapper = brokerMessageMapper;
    }

    public int insert(BrokerMessage brokerMessage){
        return brokerMessageMapper.insert(brokerMessage);
    }

    public BrokerMessage selectByMessageId(String messageId){
        return brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(), new Date());
    }

    public void failure(String messageId){
        brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }

    public List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMessageStatus status) {
        return brokerMessageMapper.queryBrokerMessageStatus4Timeout(status.getCode());
    }

    public int updatedTryCount(String messageId){
        return brokerMessageMapper.update4TryCount(messageId, new Date());
    }
}
