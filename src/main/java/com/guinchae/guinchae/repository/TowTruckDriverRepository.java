package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.TowTruckDriverModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TowTruckDriverRepository extends JpaRepository<TowTruckDriverModel, Long> {

    Optional<TowTruckDriverModel> findByEmail(String email);
}
