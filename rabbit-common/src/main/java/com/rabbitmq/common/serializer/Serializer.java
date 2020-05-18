package com.rabbitmq.common.serializer;

/**
 * @description:序列化和反序列化接口
 * @author: lzy
 * @create: 2020-05-18 22:42
 **/
public interface Serializer {

    byte[] serializerRaw(Object data);

    String serializer(Object data);

    <T> T deserializer(String content);

    <T> T deserializer(byte[] content);
}
