package com.lusvincius.desafio_emprestimo.service.strategy.imp;

import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;
import com.lusvincius.desafio_emprestimo.domain.model.Loan;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;
import com.lusvincius.desafio_emprestimo.service.strategy.LoanStrategy;
import org.springframework.stereotype.Service;

@Service
class PersonalLoanStrategy implements LoanStrategy {

  @Override
  public Loan getLoan() {
    return new Loan(LoanType.PERSONAL, 4.0);
  }

  @Override
  public boolean availableLoan(LoanEligibilityCriteria loanEligibilityCriteria) {
    System.out.println("Verificando disponibilidade de emprestimo: Pessoal");
    Double income = loanEligibilityCriteria.income();
    Integer age = loanEligibilityCriteria.age();
    String location = loanEligibilityCriteria.location();

    return isLowIncome(income) || isMiddleIncomeEligible(income, age, location);
  }

  private boolean isLowIncome(Double income) {
    return income <= 3000;
  }

  private boolean isMiddleIncomeEligible(Double income, Integer age, String location) {
    return (income > 3000 && income < 5000) && age < 30 && isLocationSP(location);
  }

  private boolean isLocationSP(String location) {
    return "SP".equals(location);
  }
}
