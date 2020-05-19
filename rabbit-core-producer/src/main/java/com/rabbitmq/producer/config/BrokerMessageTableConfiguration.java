package com.rabbitmq.producer.config;

/**
 * @description:
 * @author: lzy
 * @create: 2020-05-19 15:51
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


/**
 * 进行数据库表的创建
 */
@Configuration
public class BrokerMessageTableConfiguration {
    @Autowired
    private DataSource rabbitProducerDataSource;

    @Value("classpath:rabbit-producer-message-schema.sql")
    private Resource resource;

    public DataSourceInitializer initDataSourceInitializer(){
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    /**
     * 数据填充器，用来执行sql表结构
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(resource);
        return populator;
    }

}
