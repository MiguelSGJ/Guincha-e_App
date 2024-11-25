package com.guinchae.guinchae.service;

import com.guinchae.guinchae.repository.RequestRepository;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;


}
