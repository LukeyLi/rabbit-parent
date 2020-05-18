package com.rabbitmq.api.exception;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 17:03
 **/
public class MessageException extends Exception {
    private static final long serialVersionUID = 939529170930145380L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
