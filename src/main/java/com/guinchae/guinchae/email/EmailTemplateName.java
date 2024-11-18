package com.guinchae.guinchae.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    CAR_ADDED("car_added"),
    TOW_TRUCK_ADDED("tow_truck_added"),
    MOTORCYCLE_ADDED("motorcycle_added");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
