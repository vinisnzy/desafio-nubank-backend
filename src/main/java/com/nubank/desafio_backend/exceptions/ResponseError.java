package com.nubank.desafio_backend.exceptions;

import java.time.Instant;

public record ResponseError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
