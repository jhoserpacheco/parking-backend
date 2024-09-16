package com.nelumbo.mail.mgsbroker.event;

import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BrokerService {
    public void confirmarNAck(Channel channel, long tag) {
        try {
            channel.basicNack(tag, false, false);
        } catch (IOException errorGeneral) {
            System.err.println(" Error procesando el mensaje, Error: " + errorGeneral.getMessage());
        }
    }

    public void confirmarAck(Channel channel, long tag) {
        try {
            channel.basicAck(tag, false);
        } catch (IOException errorGeneral) {
            System.err.println(" Error procesando el mensaje, Error: " + errorGeneral.getMessage());
        }
    }
}
