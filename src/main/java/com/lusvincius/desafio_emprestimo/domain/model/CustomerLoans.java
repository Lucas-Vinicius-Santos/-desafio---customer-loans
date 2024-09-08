package com.lusvincius.desafio_emprestimo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class CustomerLoans {

  private String customer;
  private Set<Loan> loans;

  @Override
  public String toString() {
    return "CustomerLoans{" +
        "customer='" + customer + '\'' +
        ", loans=" + loans +
        '}';
  }
}
