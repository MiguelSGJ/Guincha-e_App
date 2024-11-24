package com.guinchae.guinchae.repository;

import com.guinchae.guinchae.model.RequestModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestModel,Long> {

    List<RequestModel> findAllByClient(UserModel client);
    Optional<RequestModel> findRequestModelById(Long id);
    Optional<RequestModel> findRequestModelByTowTruckDriver(TowTruckDriverModel towTruckDriver);

    @Query("SELECT r FROM RequestModel r WHERE TYPE(r.towTruckDriver) = TowTruckDriverModel  AND r.towTruckDriver.id = :driverId AND r.status = 'ASSIGNED'")
    Optional<RequestModel> findRequestModelByTowTruckDriver(@Param(
            "driverId"
    )Long driverId);
}
