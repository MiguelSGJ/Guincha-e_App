package com.guinchae.guinchae.service;

import com.guinchae.guinchae.email.EmailService;
import com.guinchae.guinchae.email.EmailTemplateName;
import com.guinchae.guinchae.model.CarModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.repository.UserRepository;
import com.guinchae.guinchae.model.dto.CarRegistrationDto;
import com.guinchae.guinchae.repository.CarRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public void registerCar(CarRegistrationDto carRegistrationDto) throws MessagingException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var car = CarModel.builder()
                .userModel(userModel)
                .towHookPresent(carRegistrationDto.isTowHookPresent())
                .eletricParkingBrake(carRegistrationDto.isEletricParkingBrake())
                .carDriveType(carRegistrationDto.getCarDriveType())
                .licensePlate(carRegistrationDto.getLicensePlate())
                .model(carRegistrationDto.getModel())
                .brandName(carRegistrationDto.getBrandName())
                .year(carRegistrationDto.getYear())
                .weight((carRegistrationDto.getWeight()))
                .build();

        carRepository.save(car);
        this.sendCarAddedNotification(userModel, car);
    }

    public void sendCarAddedNotification(UserModel user, CarModel car) throws MessagingException {
        emailService.sendVehicleAddedEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.CAR_ADDED,
                car.getModel(),
                car.getLicensePlate(),
                "Carro adicionado a sua conta!"
        );
    }

    public List<CarModel> getAllCars(String userEmail) {
        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return carRepository.findByUserModel(userModel);
    }
}
