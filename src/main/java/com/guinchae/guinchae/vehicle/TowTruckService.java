package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.TowTruckDriver;
import com.guinchae.guinchae.user.TowTruckDriverRepository;
import com.guinchae.guinchae.user.User;
import com.guinchae.guinchae.user.UserRepository;
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

    public void registerTowTruck(TowTruckRegistration towTruckRegistration) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        TowTruckDriver driver = towTruckDriverRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não é um guincheiro"));

        var towTruck = TowTruck.builder()
                .loadCapacity(towTruckRegistration.getLoadCapacity())
                .platformLength(towTruckRegistration.getPlatformLength())
                .licensePlate(towTruckRegistration.getLicensePlate())
                .model(towTruckRegistration.getModel())
                .brandName(towTruckRegistration.getBrandName())
                .year(towTruckRegistration.getYear())
                .townTruckDriver(driver)
                .build();

        towTruckRepository.save(towTruck);
    }

    public List<TowTruck> getAllTowTrucks(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return towTruckRepository.findByUser(user);
    }
}
