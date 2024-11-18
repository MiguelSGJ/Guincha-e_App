package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.dto.TowTruckDriverRegistrationDto;
import com.guinchae.guinchae.repository.RoleRepository;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TowTruckDriverService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;

    public void registerTowTruckDriver(TowTruckDriverRegistrationDto registration) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if(user instanceof TowTruckDriverModel){
            throw new RuntimeException("User is already registered as a tow truck driver");
        }

        var driverRole = roleRepository.findByName("TTDRIVER")    // ttdriver -> TowTruckDriver
                .orElseThrow(() -> new RuntimeException("ttdriver Role not found"));


        // Permanece com o id e a role padrão de usuario
        var driver = TowTruckDriverModel.builder()
                .Id(user.getId())
                .roles(user.getRoles())
                .cnhNumber(registration.getCnhNumber())
                .build();

        // Adiciona a role de tow truck driver a lista de roles
        driver.getRoles().add(driverRole);

        towTruckDriverRepository.save(driver);
    }
}
