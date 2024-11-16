package com.guinchae.guinchae.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String model;
    private String brandName;
    private int year;
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_user_id")
    private UserModel userModel;
}
