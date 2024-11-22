package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "_tow_truck_driver")
public class TowTruckDriverModel extends UserModel {

    @OneToMany(mappedBy = "towTruckDriver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TowTruckModel> towTruckModel;
    private String cnhNumber;
    private boolean isAvailable;

    private boolean isAssigned;
}
