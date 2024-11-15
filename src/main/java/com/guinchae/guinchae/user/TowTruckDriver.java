package com.guinchae.guinchae.user;

import com.guinchae.guinchae.vehicle.TowTruck;
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
@Table(name = "_towTruckDriver")
public class TowTruckDriver extends User {

    @OneToMany(mappedBy = "townTruckDriver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TowTruck> towTruck;
    private String cnhNumber;
}
