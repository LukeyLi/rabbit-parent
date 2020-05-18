package com.rabbitmq.api;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 16:13
 **/
public final class MessageType {
    /**
     * 迅速消息：不需要保障消息的可靠性，也不需要做confirm确认
     */
    public final static String RAPID = "0";

    /**
     * 确认消息: 不需要保障消息的可靠性，但是会做confirm确认
     */
    public final static String CONFIRM = "1";

    /**
     * 可靠性消息：保障消息100%投递，不允许有任何消息的丢失
     */
    public final static String RELIANT = "2";

}
