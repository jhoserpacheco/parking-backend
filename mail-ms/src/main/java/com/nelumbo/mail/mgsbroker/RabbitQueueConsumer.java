package com.nelumbo.mail.mgsbroker;

import com.nelumbo.mail.dto.EmailDto;
import com.nelumbo.mail.mgsbroker.event.BrokerService;
import com.nelumbo.mail.service.impl.EmailServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitQueueConsumer {

    private final BrokerService brokerService;
    private final EmailServiceImpl emailServiceImpl;


    @RabbitListener(queues = "${rabbitmq.routing.email.queue.user}", containerFactory = "listenerRabbit")
    public void receiveVerificationEmail(@Payload EmailDto emailDto, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            Thread.sleep(3000);
            emailServiceImpl.sendEmail(emailDto);
            brokerService.confirmarAck(channel, tag);
        }catch (Exception errorGeneral){
            brokerService.confirmarNAck(channel, tag);
            System.err.println(errorGeneral.getMessage());
            throw new RuntimeException(errorGeneral);
        }
    }

    @RabbitListener(queues = "${rabbitmq.routing.email.queue.parking}", containerFactory = "listenerRabbit")
    public void receiveParkingEmail(@Payload EmailDto emailDto, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            Thread.sleep(3000);
            emailServiceImpl.sendEmail(emailDto);
            brokerService.confirmarAck(channel, tag);
        }catch (Exception errorGeneral){
            brokerService.confirmarNAck(channel, tag);
            System.err.println(errorGeneral.getMessage());
            throw new RuntimeException(errorGeneral);
        }
    }
}
