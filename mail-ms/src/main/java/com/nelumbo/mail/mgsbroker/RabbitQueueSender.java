package com.nelumbo.mail.mgsbroker;

import com.nelumbo.mail.dto.EmailDto;
import com.nelumbo.mail.mgsbroker.domain.RabbitBindingRoute;
import com.nelumbo.mail.mgsbroker.event.RespuestaBroker;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RabbitQueueSender {

    @Resource(name = "productorRabbit")
    RabbitTemplate productorRabbit;

    @Autowired
    private RabbitBindingRoute rabbitBindingRoute;

    public RespuestaBroker send(EmailDto email){
        try {
            CorrelationData confirmaCorrelation  = new CorrelationData();
            productorRabbit.convertAndSend(rabbitBindingRoute.getExchange(),
                    rabbitBindingRoute.getRoutingKey(), email, confirmaCorrelation);
            CorrelationData.Confirm resultado = confirmaCorrelation.getFuture().get(15, TimeUnit.SECONDS);
            return RespuestaBroker.builder().solicitudExito(resultado.isAck()).mensajeRespuesta(resultado.getReason()).build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return RespuestaBroker.builder().solicitudExito(false).mensajeRespuesta("Se ha interrumpido el proceso").build();
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el email " + e.getMessage());
        }
    }
}