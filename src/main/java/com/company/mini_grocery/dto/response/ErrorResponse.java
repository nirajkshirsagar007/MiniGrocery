package com.company.mini_grocery.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private boolean success;
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
