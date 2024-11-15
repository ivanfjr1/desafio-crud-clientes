package com.devsuperior.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDto {

    private Long id;

    @NotEmpty
    @NotNull(message = "Campo name obrigatório!")
    private String name;

    private String cpf;

    private Double income;

    @PastOrPresent(message = "Não pode ser informado uma data futura")
    private LocalDate birthDate;

    private Integer children;

}
