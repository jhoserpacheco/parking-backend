package com.nelumbo.mail.service;

import com.nelumbo.mail.dto.EmailDto;
import com.nelumbo.mail.entity.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IEmailService {
    Email sendEmail(EmailDto emailDto);

    Page<Email> findAll(Pageable pageable);

    Optional<Email> findById(UUID emailId);

}
