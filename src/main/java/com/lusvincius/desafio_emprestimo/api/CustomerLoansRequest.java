package com.lusvincius.desafio_emprestimo.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record CustomerLoansRequest(
    @NotBlank(message = "Nome do cliente deve ser informado") String name,
    @CPF(message = "CPF inválido") @NotBlank(message = "CPF do cliente deve ser informado") String cpf,
    @Min(value = 18, message = "Idade deve ser maior ou igual a 18") Integer age,
    @Min(value = 1000, message = "Renda deve ser maior ou igual a 1000") Double income,
    @NotBlank(message = "UF da cidade deve ser informada") String location
) {
}
