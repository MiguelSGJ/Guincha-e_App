package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.MotorcycleModel;
import com.guinchae.guinchae.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<MotorcycleModel, Long> {

    List<MotorcycleModel> findByUserModel(UserModel userModel);
}
