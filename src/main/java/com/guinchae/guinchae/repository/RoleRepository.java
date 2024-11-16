package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    Optional<RoleModel> findByName(String role);
}
