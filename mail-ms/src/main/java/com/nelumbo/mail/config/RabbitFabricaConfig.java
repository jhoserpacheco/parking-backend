package com.nelumbo.mail.config;

import com.nelumbo.mail.mgsbroker.domain.RabbitConexionProps;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Data
@Configuration
@EnableRabbit
public class RabbitFabricaConfig {

    private final ApplicationEventPublisher publisher;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitFabricaConfig.class);

    private  static  final String CONSUMER_NAME = "rutaBroker";

    @Bean("conexionRabbit")
    public ConnectionFactory conexionRabbit(@Qualifier("rabbitConexionProps") RabbitConexionProps rabbitConexionProps){
        return fabricaConexionRabbit(rabbitConexionProps);
    }

    @Bean("productorRabbit")
    public RabbitTemplate productorRabbit(@Qualifier("conexionRabbit") ConnectionFactory connectionFactory){
        return fabricaProductorRabbit(connectionFactory, jsonMessageConverter());
    }

    @Bean("listenerRabbit")
    public SimpleRabbitListenerContainerFactory listenerRabbit(@Qualifier("conexionRabbit") ConnectionFactory connectionFactory){
        return hospSyncFactory(connectionFactory, jsonMessageConverter());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    public ConnectionFactory fabricaConexionRabbit(RabbitConexionProps rabbitConexionProps){
        com.rabbitmq.client.ConnectionFactory fabrica = new com.rabbitmq.client.ConnectionFactory();
        fabrica.setHost(rabbitConexionProps.getHost());
        fabrica.setPort(rabbitConexionProps.getPort());
        fabrica.setUsername(rabbitConexionProps.getUsername());
        fabrica.setPassword(rabbitConexionProps.getPassword());
        fabrica.setVirtualHost(rabbitConexionProps.getVirtualHost());
        fabrica.setAutomaticRecoveryEnabled(false);
        fabrica.setConnectionTimeout(10000);

        try {
            if(rabbitConexionProps.isSslenabled()) fabrica.useSslProtocol();
        }catch (KeyManagementException | NoSuchAlgorithmException e){
            LOG.error("{}: Error creando la conexion con rabbit sslProtocol, Error: {}", rabbitConexionProps.getBrokerTag(),e.getMessage());
        }
        CachingConnectionFactory fabricaConexionRabbit = new CachingConnectionFactory(fabrica);
        fabricaConexionRabbit.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        fabricaConexionRabbit.setPublisherReturns(true);

        return fabricaConexionRabbit;
    }

    public RabbitTemplate fabricaProductorRabbit(final ConnectionFactory connectionFactory, AbstractJackson2MessageConverter messageConverter){
        final RabbitTemplate fabricaProductor = new RabbitTemplate(connectionFactory);

        //formato del mensaje que se publicara (JSON,XML,etc)
        fabricaProductor.setMessageConverter(messageConverter);

        //confirmar si el mesaje fue recibido correctamente
        fabricaProductor.setMandatory(true);

        /*fabricaProductor.setReturnsCallback((message,replyCode,replyText,exchange,routingKey)->
                publisher.publishEvent(new BrokerEvent(new ErrorBrokerEvent(message,replyCode,replyText,exchange,routingKey))));
*/
        return fabricaProductor;
    }

    public SimpleRabbitListenerContainerFactory hospSyncFactory(ConnectionFactory connectionFactory, AbstractJackson2MessageConverter messageConverter){

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(messageConverter);
        factory.setConnectionFactory(connectionFactory);

        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        factory.setPrefetchCount(1);

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);

        factory.setMissingQueuesFatal(false);
        factory.setConsumerTagStrategy(
                q-> CONSUMER_NAME.concat(".").concat(System.getProperty("user.name")));

        return factory;


    }



}
