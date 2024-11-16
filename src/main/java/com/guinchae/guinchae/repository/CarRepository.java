package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.CarModel;
import com.guinchae.guinchae.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByUserModel(UserModel userModel);
}
