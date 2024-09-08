package com.lusvincius.desafio_emprestimo.service;


import com.lusvincius.desafio_emprestimo.api.CustomerLoansRequest;
import com.lusvincius.desafio_emprestimo.api.CustomerLoansResponse;
import com.lusvincius.desafio_emprestimo.domain.model.CustomerLoans;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;
import com.lusvincius.desafio_emprestimo.service.factory.LoansFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoansService {

  public final LoansFactory loansFactory;

  public CustomerLoansResponse calculate(CustomerLoansRequest request) {
    LoanEligibilityCriteria loanEligibilityCriteria = new LoanEligibilityCriteria(request.age(), request.income(), request.location());
    CustomerLoans customerLoans = new CustomerLoans(request.name(), loansFactory.availableLoans(loanEligibilityCriteria));

    return CustomerLoansResponse.toCustomerLoansResponse(customerLoans);
  }
}
