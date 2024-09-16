package com.nelumbo.mail.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nelumbo.mail.dto.EmailDto;
import com.nelumbo.mail.dto.StatusEmailEnum;
import com.nelumbo.mail.entity.Email;
import com.nelumbo.mail.mapper.EmailMapper;
import com.nelumbo.mail.repository.EmailRepository;
import com.nelumbo.mail.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Override
    public Email sendEmail(EmailDto emailDto) {
        EmailMapper mapper = new EmailMapper();
        Email email = mapper.emailTo(emailDto, new TypeReference<>(){});
        email.setCreatedAt(LocalDateTime.now());
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            boolean isHtml = true;
            helper.setFrom(email.getEmailFrom());
            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText(), isHtml);

            //emailSender.send(mimeMessage);
            log.info("Sending email to {}", email.getEmailTo());
            email.setStatus(StatusEmailEnum.SENT.name());

        } catch (MailException e) {
            email.setStatus(StatusEmailEnum.ERROR.name());
            throw new RuntimeException("No se puedo enviar el email.", e.getCause());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return emailRepository.save(email);
    }

    @Override
    public Page<Email> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    @Override
    public Optional<Email> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}
