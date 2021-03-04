package com.java.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue delOrderCacheQueue() {
        return new Queue("delOrderCache");
    }
    @Bean
    public Queue delItemCacheQueue() {
        return new Queue("delItemCache");
    }
    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue");
    }

}
