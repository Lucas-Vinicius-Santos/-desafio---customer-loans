package com.lusvincius.desafio_emprestimo.expection;

import java.time.LocalDateTime;

public record ErrorDetails(
    LocalDateTime timestamp,
    String message,
    String details,
    int http_status
) {
}
