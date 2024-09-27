package com.nelumbo.mail.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nelumbo.mail.dto.EmailParkingDto;
import com.nelumbo.mail.dto.ResponseEmailDto;
import com.nelumbo.mail.dto.StatusEmailEnum;
import com.nelumbo.mail.entity.Email;
import com.nelumbo.mail.entity.EmailParking;
import com.nelumbo.mail.exceptions.EmailNotFoundException;
import com.nelumbo.mail.mapper.EmailMapper;
import com.nelumbo.mail.repository.EmailParkingRepository;
import com.nelumbo.mail.repository.EmailRepository;
import com.nelumbo.mail.util.Constants;
import com.nelumbo.mail.util.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailParkingServiceImpl {

    private final EmailParkingRepository emailParkingRepository;
    private final MongoTemplate mongoTemplate;
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

            response.setId(emailParking.getId().toString());
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

    public ResponseEmailDto getStatusEmail(ObjectId id) {
        Optional<EmailParking> emailParking = emailParkingRepository.findById(id);
        if (emailParking.isPresent()) {
            return new ResponseEmailDto(emailParking.get().getId().toString(),emailParking.get().getStatus());
        }
        throw new EmailNotFoundException("Email id not found");
    }

    private String buildSubject(EmailParkingDto emailParkingDto){
        return emailParkingDto.getSubject() + " - " + emailParkingDto.getVehiclePlate();

    }

    private String buildBody(EmailParkingDto emailParkingDto){
        return EmailTemplate.generateBodyEmailParking(
                emailParkingDto.getParkingName(), emailParkingDto.getVehiclePlate());
    }

    public List<EmailParking> search(String query){
        Query searchQuery = new Query();
        searchQuery.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("text").regex(".*" + query + ".*", "i"),
                        Criteria.where("subject").regex(".*" + query + ".*", "i")
                )
        );
        return mongoTemplate.find(searchQuery, EmailParking.class);
    }

    public List<EmailParking> findAll(String status){
        if (status != null){
            String upperStatus = status.toUpperCase();
            boolean valid = Arrays.stream(Constants.EmailTemplate.StatusEmail.values())
                    .anyMatch(statusEmail ->
                            statusEmail.name().equalsIgnoreCase(upperStatus));
            if (valid){
                return emailParkingRepository.findAllByStatus(upperStatus);
            }
            throw new EmailNotFoundException(Constants.Message.STATUS_FAILED);
        }
        return emailParkingRepository.findAll();
    }
}
