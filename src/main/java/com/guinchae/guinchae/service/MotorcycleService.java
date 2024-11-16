package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.MotorcycleModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.repository.UserRepository;
import com.guinchae.guinchae.model.dto.MotorcycleRegistrationDto;
import com.guinchae.guinchae.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;

    public void registerMotorcycle(MotorcycleRegistrationDto motorcycleRegistrationDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var motorcycle = MotorcycleModel.builder()
                .userModel(userModel)
                .brandName(motorcycleRegistrationDto.getBrandName())
                .model(motorcycleRegistrationDto.getModel())
                .year(motorcycleRegistrationDto.getYear())
                .licensePlate(motorcycleRegistrationDto.getLicensePlate())
                .weight(motorcycleRegistrationDto.getWeight())
                .towStrapPoints(motorcycleRegistrationDto.getTowStrapPoints())
                .isElectric(motorcycleRegistrationDto.isElectric())
                .build();

        motorcycleRepository.save(motorcycle);
    }
}
