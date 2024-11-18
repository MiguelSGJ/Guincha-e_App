package com.guinchae.guinchae.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TowTruckRegistrationDto {

    @NotBlank(message = "A capacidade do guincho é necessária!")
    @NotEmpty(message = "A capacidade do guincho é necessária!")
    private int loadCapacity;
    @NotBlank(message = "O tamanho da plataforma do guincho é necessária!")
    @NotEmpty(message = "O tamanho da plataforma do guincho é necessária!")
    private double platformLength;
    @NotEmpty(message = "Placa do carro é necessária!")
    @NotBlank(message = "Placa do carro é necessária!")
    private String licensePlate;
    @NotEmpty(message = "Modelo do guincho é necessário!")
    @NotBlank(message = "Modelo do guincho é necessário!")
    private String model;
    @NotEmpty(message = "A marca do guincho é necessária!")
    @NotBlank(message = "A marca do guincho é necessária!")
    private String brandName;
    @NotEmpty(message = "Ano do guincho é necessário!")
    @NotBlank(message = "Ano do guincho é necessário!")
    private int year;
}
