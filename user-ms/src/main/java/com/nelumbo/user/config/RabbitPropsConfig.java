package com.nelumbo.user.config;

import com.nelumbo.user.mgsbroker.domain.RabbitConexionProps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPropsConfig {

    @Bean("rabbitConexionProps")
    @ConfigurationProperties(prefix = "rabbitmq")
    public RabbitConexionProps rabbitConexionProps(){ return RabbitConexionProps.builder().build();}
}
