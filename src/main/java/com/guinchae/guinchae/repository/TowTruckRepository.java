package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.TowTruckModel;
import com.guinchae.guinchae.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TowTruckRepository extends JpaRepository<TowTruckModel,Long> {

    List<TowTruckModel> findByUserModel(UserModel userModel);
}
