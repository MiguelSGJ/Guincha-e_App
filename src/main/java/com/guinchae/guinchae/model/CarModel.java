package com.guinchae.guinchae.model;

import com.guinchae.guinchae.model.type.CarDriveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "car")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CarModel extends VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean towHookPresent;
    private boolean eletricParkingBrake;
    private String towInstructions;
    private CarDriveType carDriveType;
}
