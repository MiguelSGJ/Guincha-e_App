package com.guinchae.guinchae.model.dto;

import com.guinchae.guinchae.model.type.CarDriveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarRegistrationDto {

    @NotEmpty(message = "Placa do carro é necessária!")
    @NotBlank(message = "Placa do carro é necessária!")
    private String licensePlate;
    @NotEmpty(message = "Modelo do carro é necessária!")
    @NotBlank(message = "Modelo do carro é necessária!")
    private String model;
    @NotEmpty(message = "Marca do carro é necessária!")
    @NotBlank(message = "Marca do carro é necessária!")
    private String brandName;
    @NotEmpty(message = "Ano do carro é necessário!")
    @NotBlank(message = "Ano do carro é necessário!")
    private int year;
    @NotEmpty(message = "Peso do carro é necessário!")
    @NotBlank(message = "Peso do carro é necessário!")
    private int weight;


    private boolean towHookPresent;
    private boolean eletricParkingBrake;

    @NotEmpty(message = "Sem informações sobre o tipo de tração do carro!")
    @NotBlank(message = "Sem informações sobre o tipo de tração do carro!")
    private CarDriveType carDriveType;
}
