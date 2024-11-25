package com.guinchae.guinchae.service;

import com.guinchae.guinchae.email.EmailService;
import com.guinchae.guinchae.email.EmailTemplateName;
import com.guinchae.guinchae.model.TowTruckModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.model.dto.TowTruckRegistrationDto;
import com.guinchae.guinchae.repository.TowTruckRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TowTruckService {

    private final TowTruckRepository towTruckRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;
    private final EmailService emailService;

    public void registerTowTruck(TowTruckRegistrationDto towTruckRegistrationDto) throws MessagingException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        TowTruckDriverModel towTruckDriverModel = towTruckDriverRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Verifica se o usuario possui a role de tow truck driver
        boolean role = towTruckDriverModel.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_TTDRIVER"));

        if(!role) {
            throw new RuntimeException("Usuário não possui permissão para adicionar um guincho!");
        }

        TowTruckDriverModel driver = towTruckDriverRepository.findById(towTruckDriverModel.getId())
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
        this.sendTowTruckNotification(driver, towTruck);
    }

    public void sendTowTruckNotification(TowTruckDriverModel user, TowTruckModel towTruck) throws MessagingException {
        emailService.sendVehicleAddedEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.TOW_TRUCK_ADDED,
                towTruck.getModel(),
                towTruck.getLicensePlate(),
                "Guincho adicionado a sua conta!"
        );
    }

    public List<TowTruckModel> getAllTowTrucks(String userEmail) {
        TowTruckDriverModel towTruckDriver = towTruckDriverRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return towTruckRepository.findByUserModel(towTruckDriver);
    }
}
