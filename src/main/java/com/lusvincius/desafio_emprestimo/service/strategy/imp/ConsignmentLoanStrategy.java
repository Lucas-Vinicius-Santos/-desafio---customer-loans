package com.lusvincius.desafio_emprestimo.service.strategy.imp;

import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;
import com.lusvincius.desafio_emprestimo.domain.model.Loan;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;
import com.lusvincius.desafio_emprestimo.service.strategy.LoanStrategy;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
public class ConsignmentLoanStrategy implements LoanStrategy {

  @Override
  public Loan getLoan() {
    return new Loan(LoanType.CONSIGNMENT, 2.0);
  }

  @Override
  public boolean availableLoan(LoanEligibilityCriteria loanEligibilityCriteria) {
    return loanEligibilityCriteria.income() >= 5000;
  }
}