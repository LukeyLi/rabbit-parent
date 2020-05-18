package com.rabbitmq.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 20:58
 **/
@Slf4j
public class AsyncBaseQueue {

    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAsync = new ThreadPoolExecutor(THREAD_SIZE,
            2 * THREAD_SIZE + 1,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("rabbitmq_client_async_sender");
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.error("async sender is error rejected, runable：{}, excutor:{}", r, executor);
                }
            });
    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }
}
