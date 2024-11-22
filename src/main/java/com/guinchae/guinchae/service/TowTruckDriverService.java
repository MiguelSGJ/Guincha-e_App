package com.guinchae.guinchae.service;

import com.guinchae.guinchae.email.EmailService;
import com.guinchae.guinchae.email.EmailTemplateName;
import com.guinchae.guinchae.model.CarModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.model.dto.TowTruckDriverRegistrationDto;
import com.guinchae.guinchae.repository.RoleRepository;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TowTruckDriverService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;
    private final EmailService emailService;

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
                .isAvailable(false)
                .build();

        // Adiciona a role de tow truck driver a lista de roles
        driver.getRoles().add(driverRole);

        towTruckDriverRepository.save(driver);
    }

    public void changeTowTruckDriverAvailability(boolean isAvailable) {
        String driverEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var driver = towTruckDriverRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("user not found"));

        driver.setAvailable(isAvailable);
    }

    public void sendCarAddedNotification(TowTruckDriverModel driverModel) throws MessagingException {
        emailService.sendTowTruckDriverEmail(
                driverModel.getEmail(),
                driverModel.fullName(),
                EmailTemplateName.NEW_TOW_TRUCK_DRIVER,
                "Parabéns, agora você é um guincheiro pelo Guincha-E!"
        );
    }
}
