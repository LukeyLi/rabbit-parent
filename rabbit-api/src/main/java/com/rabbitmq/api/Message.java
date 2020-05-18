package com.rabbitmq.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 15:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -6427856076762208161L;

    /* 消息的唯一Id */
    private String messageId;
    /* 消息的主题 */
    private String topic;
    /** 消息的路由规则 */
    private String routingKey;
    /* 消息的附加属性 */
    private Map<String, Object> attributes = new HashMap<String, Object>();
    /* 延迟消息的参数配置 */
    private int delayMills;
    /*消息类型, 默认为confirm模式*/
    private String messageType = MessageType.CONFIRM;
}
