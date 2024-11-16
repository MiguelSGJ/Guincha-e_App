package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_tow_truck_driver")
public class TowTruckDriverModel extends UserModel {

    @OneToMany(mappedBy = "towTruckDriver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TowTruckModel> towTruckModel;
    private String cnhNumber;
}
