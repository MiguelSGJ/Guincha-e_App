package com.guinchae.guinchae.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class MotorcycleRegistration {

    @NotEmpty(message = "Placa da moto é necessária!")
    @NotBlank(message = "Placa da moto é necessária!")
    private String licensePlate;
    @NotEmpty(message = "Modelo da moto é necessária!")
    @NotBlank(message = "Modelo da moto é necessária!")
    private String model;
    @NotEmpty(message = "Marca da moto é necessária!")
    @NotBlank(message = "Marca da moto é necessária!")
    private String brandName;
    @NotEmpty(message = "Ano da moto é necessário!")
    @NotBlank(message = "Ano da moto é necessário!")
    private int year;
    @NotEmpty(message = "Peso da moto é necessário!")
    @NotBlank(message = "Peso da moto é necessário!")
    private int weight;

    @NotEmpty(message = "A Quantidade de pontos de reboque da moto é necessário!")
    @NotBlank(message = "A Quantidade de pontos de reboque da moto é necessário!")
    private int towStrapPoints;
    private boolean isEletric;


}
