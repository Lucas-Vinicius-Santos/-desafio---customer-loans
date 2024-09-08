package com.lusvincius.desafio_emprestimo.api;

import com.lusvincius.desafio_emprestimo.domain.model.CustomerLoans;

import java.util.Set;
import java.util.stream.Collectors;

public record CustomerLoansResponse(
    String customer,
    Set<LoansResponse> loans
) {

  public static CustomerLoansResponse toCustomerLoansResponse(CustomerLoans customerLoans) {
    return new CustomerLoansResponse(
        customerLoans.getCustomer(),
        mapLoans(customerLoans)
    );
  }

  private static Set<LoansResponse> mapLoans(CustomerLoans customerLoans) {
    return customerLoans.getLoans().stream()
        .map(loan -> new LoansResponse(loan.getType(), loan.getInterestRate()))
        .collect(Collectors.toSet());
  }
}