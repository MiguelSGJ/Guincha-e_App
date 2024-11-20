package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestModel,Long> {

    List<RequestModel> findAllById(Long id);
    Optional<RequestModel> findRequestModelById(Long id);
}
