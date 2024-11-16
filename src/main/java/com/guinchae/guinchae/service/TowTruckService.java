package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.TowTruckModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import com.guinchae.guinchae.model.dto.TowTruckRegistrationDto;
import com.guinchae.guinchae.repository.TowTruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TowTruckService {

    private final TowTruckRepository towTruckRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;
    private final UserRepository userRepository;

    public void registerTowTruck(TowTruckRegistrationDto towTruckRegistrationDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        TowTruckDriverModel driver = towTruckDriverRepository.findById(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não é um guincheiro"));

        var towTruck = TowTruckModel.builder()
                .loadCapacity(towTruckRegistrationDto.getLoadCapacity())
                .platformLength(towTruckRegistrationDto.getPlatformLength())
                .licensePlate(towTruckRegistrationDto.getLicensePlate())
                .model(towTruckRegistrationDto.getModel())
                .brandName(towTruckRegistrationDto.getBrandName())
                .year(towTruckRegistrationDto.getYear())
                .towTruckDriver(driver)
                .build();

        towTruckRepository.save(towTruck);
    }

    public List<TowTruckModel> getAllTowTrucks(String userEmail) {
        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return towTruckRepository.findByUserModel(userModel);
    }
}
