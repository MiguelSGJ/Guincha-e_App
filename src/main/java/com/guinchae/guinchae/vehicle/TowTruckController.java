package com.guinchae.guinchae.vehicle;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tow-truck")
@RequiredArgsConstructor
@Tag(name = "tow_truck_vehicle")
public class TowTruckController {

    private final TowTruckService towTruckService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerTowTruck(
            @RequestBody @Valid TowTruckRegistration towTruckRegistration
    ) {
        towTruckService.registerTowTruck(towTruckRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @ResponseStatus
    public ResponseEntity<List<TowTruck>> getAllTowTrucks() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TowTruck> towTrucks = towTruckService.getAllTowTrucks(userEmail);

        return ResponseEntity.ok(towTrucks);
    }
}
