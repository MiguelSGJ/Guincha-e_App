package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tow_truck")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TowTruckModel extends VehicleModel {

    private int loadCapacity;
    private double platformLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private TowTruckDriverModel driver;
}
