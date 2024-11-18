package com.guinchae.guinchae.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TowTruckDriverRegistrationDto {

    @NotEmpty(message = "O número da cnh não pode estar vazia!")
    @NotBlank(message = "O número da cnh não pode estar vazia!")
    private String cnhNumber;
}
