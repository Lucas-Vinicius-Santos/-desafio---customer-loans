package com.lusvincius.desafio_emprestimo.web;

import com.lusvincius.desafio_emprestimo.api.CustomerLoansRequest;
import com.lusvincius.desafio_emprestimo.api.CustomerLoansResponse;
import com.lusvincius.desafio_emprestimo.service.LoansService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoansController {

  private final LoansService loansService;

  @PostMapping("customer-loans")
  public ResponseEntity<CustomerLoansResponse> calculate(@RequestBody @Valid CustomerLoansRequest request) {
    return ResponseEntity.ok().body(loansService.calculate(request));
  }
}
