package com.guinchae.guinchae.controller;

import com.guinchae.guinchae.model.dto.RequestDto;
import com.guinchae.guinchae.service.RequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("request")
@RequiredArgsConstructor
@Tag(name = "Request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/create-request")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRequest(
            @RequestBody @Valid RequestDto request
            ) {
        requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/assign-request")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> assignRequest(
            @RequestBody @Valid Long requestId
    ) {
        requestService.assignDriver(requestId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/response-request")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> driverResponseRequest(
            @RequestBody @Valid Long requestId, Boolean response
    ) {
        requestService.respondToRequest(requestId, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-requests")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRequests() {
        requestService.getAllRequests();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
