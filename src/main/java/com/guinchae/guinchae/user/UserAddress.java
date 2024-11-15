package com.guinchae.guinchae.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class UserAddress {

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
