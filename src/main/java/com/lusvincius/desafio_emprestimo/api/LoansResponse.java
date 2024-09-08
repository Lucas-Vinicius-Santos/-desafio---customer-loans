package com.lusvincius.desafio_emprestimo.api;

import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;

public record LoansResponse(
    LoanType type,
    Double interestRate
) {
}
