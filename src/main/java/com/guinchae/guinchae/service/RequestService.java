package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.RequestModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.model.dto.RequestDto;
import com.guinchae.guinchae.model.type.RequestStatusType;
import com.guinchae.guinchae.repository.RequestRepository;
import com.guinchae.guinchae.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public void createRequest(RequestDto requestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var request = RequestModel.builder()
                .client(userModel)
                .pickupAddress(requestDto.getPickupLocation())
                .message(requestDto.getMessage())
                .status(RequestStatusType.PENDING) // Status inicial
                .build();

        requestRepository.save(request);
    }

    public void updateRequest(Long id, RequestStatusType requestStatusType) {
        RequestModel request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não foi encontrada!"));

        request.setStatus(requestStatusType);
    }

    public List<RequestModel> getAllRequests() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return requestRepository.findAllById(user.getId());
    }
}
