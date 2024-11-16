package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_tow_truck")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TowTruckModel extends VehicleModel {

    private int loadCapacity;
    private double platformLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tow_truck_driver_id")
    private TowTruckDriverModel towTruckDriver;

}
