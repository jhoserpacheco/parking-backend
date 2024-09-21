package com.nelumbo.mail.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nelumbo.mail.dto.EmailParkingDto;
import com.nelumbo.mail.dto.ResponseEmailDto;
import com.nelumbo.mail.dto.StatusEmailEnum;
import com.nelumbo.mail.entity.EmailParking;
import com.nelumbo.mail.mapper.EmailMapper;
import com.nelumbo.mail.repository.EmailParkingRepository;
import com.nelumbo.mail.util.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailParkingServiceImpl {

    private final EmailParkingRepository emailParkingRepository;
    private final JavaMailSender emailSender;
    @Value("${client.emailservice.email}")
    private String emailFrom;

    public ResponseEmailDto sendEmail(EmailParkingDto emailParkingDto) {
        EmailMapper mapper = new EmailMapper();
        ResponseEmailDto response = new ResponseEmailDto();
        EmailParking email = mapper.emailTo(emailParkingDto, new TypeReference<>(){});
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            boolean isHtml = true;
            helper.setFrom(emailFrom);
            helper.setTo(email.getEmailTo());
            helper.setSubject(buildSubject(emailParkingDto));
            helper.setText(buildBody(emailParkingDto), isHtml);

            //emailSender.send(mimeMessage);
            log.info("Sending email to {}", email.getEmailTo());
            email.setStatus(StatusEmailEnum.SENT.name());
            email.setEmailFrom(emailFrom);
            EmailParking emailParking = emailParkingRepository.save(email);

            response.setId(emailParking.getId());
            response.setStatus(emailParking.getStatus());
        } catch (MailException e) {
            email.setStatus(StatusEmailEnum.ERROR.name());
            response.setStatus(StatusEmailEnum.ERROR.name());
            throw new RuntimeException("No se puedo enviar el email.", e.getCause());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public ResponseEmailDto getStatusEmail(UUID id) {
        Optional<EmailParking> emailParking = emailParkingRepository.findById(id);
        if (emailParking.isPresent()) {
            return new ResponseEmailDto(emailParking.get().getId(),emailParking.get().getStatus());
        }
        return new ResponseEmailDto(id,StatusEmailEnum.ERROR.name());
    }

    private String buildSubject(EmailParkingDto emailParkingDto){
        return emailParkingDto.getSubject() + " - " + emailParkingDto.getVehiclePlate();

    }

    private String buildBody(EmailParkingDto emailParkingDto){
        return EmailTemplate.generateBodyEmailParking(
                emailParkingDto.getParkingName(), emailParkingDto.getVehiclePlate());
    }
}
