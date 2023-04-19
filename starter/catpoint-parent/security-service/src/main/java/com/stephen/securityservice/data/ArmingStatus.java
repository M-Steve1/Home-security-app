package com.stephen.securityservice.data;

import java.awt.*;

public enum ArmingStatus {
    DISARMED ("Disarmed", new Color(120, 200, 30)),
    ARMED_HOME ("Armed - at Home", new Color(190, 180, 50)),
    ARMED_AWAY ("Armed - Away", new Color(170, 30, 150));

    private final String description;
    private final Color color;

    ArmingStatus(String description, Color color) {
        this.description = description;
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public Color getColor() {
        return color;
    }
}
