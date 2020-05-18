package com.rabbitmq.api.exception;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 17:10
 **/
public class MessageRunTimeException extends RuntimeException {
    private static final long serialVersionUID = -341589124098281543L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
