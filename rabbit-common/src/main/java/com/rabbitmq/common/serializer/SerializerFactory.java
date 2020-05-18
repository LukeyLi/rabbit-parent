package com.rabbitmq.common.serializer;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 22:41
 **/
public interface SerializerFactory {
    Serializer create();
}
