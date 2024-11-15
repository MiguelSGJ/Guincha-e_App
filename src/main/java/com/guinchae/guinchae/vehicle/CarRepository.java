package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
}
