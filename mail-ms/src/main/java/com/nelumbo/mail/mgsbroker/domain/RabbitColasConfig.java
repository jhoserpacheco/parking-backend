package com.nelumbo.mail.mgsbroker.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitColasConfig {

    @Bean("rutaBroker")
    public RabbitBindingRoute rutaBroker(
            @Value("${rabbitmq.routing.email.exchange}")
            String exchange,
            @Value("${rabbitmq.routing.email.routingKey.user}")
            String routingKey){
        return RabbitBindingRoute.builder().exchange(exchange).routingKey(routingKey).build();
    }
}
