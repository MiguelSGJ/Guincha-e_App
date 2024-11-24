package com.guinchae.guinchae.controller;

import com.guinchae.guinchae.model.dto.MotorcycleRegistrationDto;
import com.guinchae.guinchae.service.MotorcycleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("motorcycle")
@RequiredArgsConstructor
@Tag(name = "Motorcycle_vehicle")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerMotorcycle(
            @RequestBody @Valid MotorcycleRegistrationDto motorcycleRegistrationDto) throws MessagingException {
        motorcycleService.registerMotorcycle(motorcycleRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
