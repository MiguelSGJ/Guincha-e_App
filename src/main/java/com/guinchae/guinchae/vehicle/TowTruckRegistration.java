package com.guinchae.guinchae.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TowTruckRegistration {

    @NotBlank(message = "A capacidade do guincho é necessária!")
    @NotEmpty(message = "A capacidade do guincho é necessária!")
    private int loadCapacity;
    @NotBlank(message = "O tamanho da plataforma do guincho é necessária!")
    @NotEmpty(message = "O tamanho da plataforma do guincho é necessária!")
    private double platformLength;
    @NotEmpty(message = "Placa do carro é necessária!")
    @NotBlank(message = "Placa do carro é necessária!")
    private String licensePlate;
    @NotEmpty(message = "Modelo do carro é necessária!")
    @NotBlank(message = "Modelo do carro é necessária!")
    private String model;
    @NotEmpty(message = "Marca do carro é necessária!")
    @NotBlank(message = "Marca do carro é necessária!")
    private String brandName;
    @NotEmpty(message = "Ano do carro é necessária!")
    @NotBlank(message = "Ano do carro é necessária!")
    private int year;
}
