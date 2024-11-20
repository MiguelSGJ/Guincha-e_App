package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.TowTruckDriverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TowTruckDriverRepository extends JpaRepository<TowTruckDriverModel, Long> {

    Optional<TowTruckDriverModel> findByEmail(String email);

    // Metodo para criar uma lista de guincheiros que estao disponiveis para receber solicitações de usuarios
    @Query("SELECT d FROM TowTruckDriverModel d WHERE d.isAvailable = true")
    List<TowTruckDriverModel> findAvailableDrivers();
}
