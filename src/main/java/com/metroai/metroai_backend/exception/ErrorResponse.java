package com.metroai.metroai_backend.exception;

import java.time.LocalDateTime;

public record ErrorResponse(

        String message,

        int status,

        LocalDateTime timestamp

) {
}