package com.stephen.securityservice.data;

import java.awt.*;

public enum AlarmStatus {
    NO_ALARM ("Cool and Good", new Color(120, 200, 30)),
    PENDING ("I'm in Danger", new Color(200, 150, 20)),
    ALARM ("Help!!!!!", new Color(250, 80, 50));

    private final String description;
    private final Color color;
    AlarmStatus(String description, Color color) {
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
