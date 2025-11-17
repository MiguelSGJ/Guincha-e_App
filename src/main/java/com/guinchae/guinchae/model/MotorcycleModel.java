package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "motorcycle")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleModel extends VehicleModel {

    private int towStrapPoints; // Quantidade de pontos de amarração da moto
    private boolean isElectric;
}
