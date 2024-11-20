package com.guinchae.guinchae.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestDto {

    @NotBlank(message = "A sua localização não pode estar vazia!")
    @NotEmpty(message = "A sua localização não pode estar vazia!")
    private String pickupLocation;
    // Não coloquei a validação porque um usuario pode não querer colocar uma mensagem adicional quando fizer a solicitação de um guincho!
    private String message;

}
