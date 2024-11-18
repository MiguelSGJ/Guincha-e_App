package com.guinchae.guinchae.controller;

import com.guinchae.guinchae.model.CarModel;
import com.guinchae.guinchae.service.CarService;
import com.guinchae.guinchae.model.dto.CarRegistrationDto;
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
@RequestMapping("car")
@RequiredArgsConstructor
@Tag(name = "Car_vehicle")
public class CarController {

    private final CarService carService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerCar(
            @RequestBody @Valid CarRegistrationDto carRegistrationDto
    ) throws MessagingException {
        carService.registerCar(carRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list-cars")
    @ResponseStatus
    public ResponseEntity<List<CarModel>> getAllCars() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CarModel> carModels = carService.getAllCars(userEmail);

        return ResponseEntity.ok(carModels);
    }
}
