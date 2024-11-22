package com.guinchae.guinchae.model.type;

public enum RequestStatusType {
    PENDING,    // Corrida pendente
    ASSIGNED,   // Corrida atribuida a um motorista
    ACCEPTED,   // Corrida aceita
    REJECTED,   // Corrida rejeitada
    COMPLETED   // Corrida finalizada
}
