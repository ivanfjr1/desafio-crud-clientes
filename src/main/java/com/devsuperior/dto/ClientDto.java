package com.devsuperior.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDto {

    private Long id;

    @NotBlank(message = "Campo name obrigatório!")
    private String name;

    private String cpf;

    private Double income;

    @PastOrPresent(message = "Não pode ser informado uma data futura")
    private LocalDate birthDate;

    private Integer children;

}
