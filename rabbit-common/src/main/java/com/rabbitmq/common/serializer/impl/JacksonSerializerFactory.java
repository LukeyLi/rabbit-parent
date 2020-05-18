package com.rabbitmq.common.serializer.impl;


import com.rabbitmq.api.Message;
import com.rabbitmq.common.serializer.Serializer;
import com.rabbitmq.common.serializer.SerializerFactory;

public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
