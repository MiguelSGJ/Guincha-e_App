package com.guinchae.guinchae.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TowTruckDriverRepository extends JpaRepository<TowTruckDriver, Long> {

    Optional<TowTruckDriver> findById(Long Id);
}
