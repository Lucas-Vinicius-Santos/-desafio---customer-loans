package com.lusvincius.desafio_emprestimo.domain.model;

import com.lusvincius.desafio_emprestimo.domain.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Loan {

  private LoanType type;
  private Double interestRate;

  @Override
  public String toString() {
    return "Loan{" +
        "type=" + type +
        ", interestRate=" + interestRate +
        '}';
  }
}
