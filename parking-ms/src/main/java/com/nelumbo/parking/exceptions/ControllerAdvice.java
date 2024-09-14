package com.nelumbo.parking.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;


@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseEntity<ErrorModel> processException(
            ErrorModel errorModel, Exception exception, HttpStatus status) {
        log.error(errorModel.toString());

        Arrays.stream(exception.getStackTrace())
                .filter(stackTraceElement -> stackTraceElement.getClassName().contains("nelumbo"))
                .forEach(log::error);

        return new ResponseEntity<>(errorModel, status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorModel> exception(Exception exception) {
        ErrorModel errorModel = ErrorModel.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .build();
        return processException(errorModel, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UnsupportedJwtException.class, MissingRequestHeaderException.class,
            ExpiredJwtException.class, MalformedJwtException.class, SignatureException.class,
            SignatureException.class})
    public final ResponseEntity<ErrorModel> unauthorized(Exception exception) {
        ErrorModel errorModel = ErrorModel.builder()
                .code(HttpStatus.UNAUTHORIZED.toString())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .build();
        return processException(errorModel, exception, HttpStatus.UNAUTHORIZED);
    }
/*
    @ExceptionHandler({UserNotFoundException.class})
    public final ResponseEntity<ErrorModel> notFound(Exception exception) {
        ErrorModel errorModel = ErrorModel.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .build();
        return processException(errorModel, exception, HttpStatus.NOT_FOUND);
    }
*/
    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<ErrorModel> badRequest(Exception exception) {
        ErrorModel errorModel = ErrorModel.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .build();
        return processException(errorModel, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FeignException.class})
    public final ResponseEntity<ErrorModel> handleFeignException(FeignException exception) {
        HttpStatus status = HttpStatus.resolve(exception.status());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorModel errorModel = ErrorModel.builder()
                .code(status.toString())
                .exception(exception.getClass().getName())
                .message(extractFeignErrorMessage(exception))
                .build();

        return processException(errorModel, exception, status);
    }

    private String extractFeignErrorMessage(FeignException exception) {
        try {
            JsonNode jsonNode = objectMapper.readTree(exception.contentUTF8());
            return jsonNode.path("message").asText();
        } catch (IOException e) {
            log.error("Error parsing FeignException content: {}", exception.getMessage());
            return "Error communicating with external service";
        }
    }
}
