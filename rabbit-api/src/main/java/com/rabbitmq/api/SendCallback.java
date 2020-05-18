package com.rabbitmq.api;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 17:15
 **/
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
