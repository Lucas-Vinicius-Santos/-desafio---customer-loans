package com.lusvincius.desafio_emprestimo.service.factory;

import com.lusvincius.desafio_emprestimo.domain.model.Loan;
import com.lusvincius.desafio_emprestimo.domain.model.LoanEligibilityCriteria;
import com.lusvincius.desafio_emprestimo.service.strategy.LoanStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoansFactory {

  private final List<LoanStrategy> availableLoans;

  public Set<Loan> availableLoans(LoanEligibilityCriteria loanEligibilityCriteria) {
    Set<Loan> avaliableLoans = availableLoans.stream()
        .filter(l -> l.availableLoan(loanEligibilityCriteria))
        .map(LoanStrategy::getLoan)
        .collect(Collectors.toSet());

    return avaliableLoans;
  }
}
