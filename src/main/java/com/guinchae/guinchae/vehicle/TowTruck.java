package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.TowTruckDriver;
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
public class TowTruck extends Vehicle {

    private int loadCapacity;
    private double platformLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_towTruckDriver_id")
    private TowTruckDriver townTruckDriver;

}
