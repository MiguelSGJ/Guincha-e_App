package com.guinchae.guinchae.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "O email nao e valido!")
    @NotEmpty(message = "O email e obrigatorio")
    @NotBlank(message = "O email e obrigatorio")
    private String email;
    @NotEmpty(message = "A senha e obrigatoria")
    @NotBlank(message = "A senha e obrigatoria")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres!")
    private String password;
}
