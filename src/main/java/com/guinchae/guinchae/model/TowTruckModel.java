package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_towTruck")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TowTruckModel extends VehicleModel {

    private int loadCapacity;
    private double platformLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_towTruckDriver_id")
    private TowTruckDriverModel townTruckDriver;

}
