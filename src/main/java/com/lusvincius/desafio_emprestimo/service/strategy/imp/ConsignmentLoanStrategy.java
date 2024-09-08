package com.lusvincius.desafio_emprestimo.service.strategy.imp;

import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;
import com.lusvincius.desafio_emprestimo.domain.model.Loan;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;
import com.lusvincius.desafio_emprestimo.service.strategy.LoanStrategy;
import org.springframework.stereotype.Service;

@Service
public class ConsignmentLoanStrategy implements LoanStrategy {

  @Override
  public Loan getLoan() {
    return new Loan(LoanType.CONSIGNMENT, 2.0);
  }

  @Override
  public boolean availableLoan(LoanEligibilityCriteria loanEligibilityCriteria) {
    System.out.println("Verificando disponibilidade de emprestimo: Consignado");
    return loanEligibilityCriteria.income() >= 5000;
  }
}