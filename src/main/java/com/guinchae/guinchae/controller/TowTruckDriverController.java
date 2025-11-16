package com.guinchae.guinchae.controller;

import com.guinchae.guinchae.model.dto.TowTruckDriverRegistrationDto;
import com.guinchae.guinchae.service.RequestService;
import com.guinchae.guinchae.service.TowTruckDriverService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tow-truck-driver")
@RequiredArgsConstructor
@Tag(name = "tow-truck-driver")
public class TowTruckDriverController {

    private final TowTruckDriverService towTruckDriverService;
    private final RequestService requestService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerTowTruckDriver(TowTruckDriverRegistrationDto registrationDto) {
        towTruckDriverService.registerTowTruckDriver(registrationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-request")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRequests(){
        towTruckDriverService.getAssignedRequest();
        return ResponseEntity.ok(towTruckDriverService.getAssignedRequest());
    }
}
