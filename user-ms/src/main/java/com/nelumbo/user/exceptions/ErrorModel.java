package com.nelumbo.user.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorModel {
    private String code;
    private String exception;
    private String message;
}