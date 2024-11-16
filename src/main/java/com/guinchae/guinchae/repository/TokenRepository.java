package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenModel, Long> {

    Optional<TokenModel> findByToken(String token);
}
