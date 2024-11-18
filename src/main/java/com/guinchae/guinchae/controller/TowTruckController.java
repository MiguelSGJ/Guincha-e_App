package com.guinchae.guinchae.controller;

import com.guinchae.guinchae.model.TowTruckModel;
import com.guinchae.guinchae.service.TowTruckService;
import com.guinchae.guinchae.model.dto.TowTruckRegistrationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tow-truck")
@RequiredArgsConstructor
@Tag(name = "TowTruck_Vehicle")
public class TowTruckController {

    private final TowTruckService towTruckService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerTowTruck(
            @RequestBody @Valid TowTruckRegistrationDto towTruckRegistrationDto
    ) throws MessagingException {
        towTruckService.registerTowTruck(towTruckRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("list-tow-truck")
    @ResponseStatus
    public ResponseEntity<List<TowTruckModel>> getAllTowTrucks() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TowTruckModel> towTruckModels = towTruckService.getAllTowTrucks(userEmail);

        return ResponseEntity.ok(towTruckModels);
    }
}
