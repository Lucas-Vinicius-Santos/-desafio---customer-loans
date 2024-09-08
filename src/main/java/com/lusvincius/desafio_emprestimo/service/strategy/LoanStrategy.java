package com.lusvincius.desafio_emprestimo.service.strategy;

import com.lusvincius.desafio_emprestimo.domain.model.Loan;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;

public interface LoanStrategy {
  boolean availableLoan(LoanEligibilityCriteria loanEligibilityCriteria);

  Loan getLoan();
}
