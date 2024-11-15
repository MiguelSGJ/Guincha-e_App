package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TowTruckRepository extends JpaRepository<TowTruck,Long> {

    List<TowTruck> findByUser(User user);
}
