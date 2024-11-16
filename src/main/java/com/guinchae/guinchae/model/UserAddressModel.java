package com.guinchae.guinchae.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class UserAddressModel {

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
