package com.nelumbo.user.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Message;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBrokerEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Message message;
    private int replyCode;
    private String replyText;
    private String exchange;
    private String routingKey;

}
