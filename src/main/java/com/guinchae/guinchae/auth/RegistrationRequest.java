package com.guinchae.guinchae.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Primeiro nome e obrigatorio")
    @NotBlank(message = "Primeiro nome e obrigatorio")
    private String firstName;
    @NotEmpty(message = "Segundo nome e obrigatorio")
    @NotBlank(message = "Segundo nome e obrigatorio")
    private String lastName;
    @Email(message = "O email nao e valido!")
    @NotEmpty(message = "O email e obrigatorio")
    @NotBlank(message = "O email e obrigatorio")
    private String email;
    @NotEmpty(message = "A senha e obrigatoria")
    @NotBlank(message = "A senha e obrigatoria")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres!")
    private String password;
}
