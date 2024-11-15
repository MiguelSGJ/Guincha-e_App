package com.guinchae.guinchae.vehicle;

import com.guinchae.guinchae.user.User;
import com.guinchae.guinchae.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public void registerCar(CarRegistration carRegistration) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var car = Car.builder()
                .user(user)
                .towHookPresent(carRegistration.isTowHookPresent())
                .eletricParkingBrake(carRegistration.isEletricParkingBrake())
                .carDriveType(carRegistration.getCarDriveType())
                .licensePlate(carRegistration.getLicensePlate())
                .model(carRegistration.getModel())
                .brandName(carRegistration.getBrandName())
                .year(carRegistration.getYear())
                .weight((carRegistration.getWeight()))
                .build();

        carRepository.save(car);
    }

    public List<Car> getAllCars(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return carRepository.findByUser(user);
    }
}
