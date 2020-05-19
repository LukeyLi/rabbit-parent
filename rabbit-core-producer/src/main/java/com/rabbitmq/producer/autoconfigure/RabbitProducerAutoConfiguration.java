package com.rabbitmq.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-18 19:23
 **/

/**
 * 自动装配
 */
@Configuration
@ComponentScan({"com.rabbitmq.producer.*"})
public class RabbitProducerAutoConfiguration {
}
