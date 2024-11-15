package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.User;
import com.guinchae.guinchae.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;

    public void registerMotorcycle(MotorcycleRegistration motorcycleRegistration) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var motorcycle = Motorcycle.builder()
                .user(user)
                .brandName(motorcycleRegistration.getBrandName())
                .model(motorcycleRegistration.getModel())
                .year(motorcycleRegistration.getYear())
                .licensePlate(motorcycleRegistration.getLicensePlate())
                .weight(motorcycleRegistration.getWeight())
                .towStrapPoints(motorcycleRegistration.getTowStrapPoints())
                .isElectric(motorcycleRegistration.isElectric())
                .build();

        motorcycleRepository.save(motorcycle);
    }
}
