package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "tow_truck_driver")
public class TowTruckDriverModel extends UserModel {

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TowTruckModel> towTruckModel;

    private String cnhNumber;
    private boolean isAvailable;

    private Double latitude;
    private Double longitude;

    private boolean isAssigned;
}
