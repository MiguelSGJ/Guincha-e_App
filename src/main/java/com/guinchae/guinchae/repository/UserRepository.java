package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByCpf(String cpf);
}
