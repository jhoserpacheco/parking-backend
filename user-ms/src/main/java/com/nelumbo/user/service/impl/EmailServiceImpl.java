package com.nelumbo.user.service.impl;

import com.nelumbo.user.mgsbroker.RabbitQueueSender;
import com.nelumbo.user.mgsbroker.domain.EmailDto;
import com.nelumbo.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final RabbitQueueSender queueSender;
    private static final String STATUS_PROCESSING = Constants.EmailTemplate.StatusEmail.PROCESSING.name();

    @Value("${client.emailservice.email}")
    private String email;

    public void sendEmail(String to, String subject, String body) {
        EmailDto emailDto = new EmailDto(UUID.randomUUID().toString(), email,
                to, subject, body, STATUS_PROCESSING);

        queueSender.send(emailDto);
    }
}
