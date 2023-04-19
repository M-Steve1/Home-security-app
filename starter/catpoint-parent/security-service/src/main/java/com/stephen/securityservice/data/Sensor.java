package com.stephen.securityservice.data;

import com.google.common.collect.ComparisonChain;

import java.util.Objects;
import java.util.UUID;

public class Sensor implements Comparable<Sensor>{
    private UUID sensorId;
    private String name;
    private boolean active;
    private SensorType sensorType;

    /**
     * default constructor
     */
    public Sensor(){}

    public Sensor(String name, SensorType sensorType) {
        this.name = name;
        this.sensorType = sensorType;
        this.sensorId = UUID.randomUUID();
        this.active = Boolean.FALSE;
    }

    public UUID getSensorId() {
        return sensorId;
    }

    public void setSensorId(UUID sensorId) {
        this.sensorId = sensorId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return this.sensorId.equals(sensor.sensorId);
    }

    public int hashCode() {
        return Objects.hash(this.sensorId);
    }

    @Override
    public int compareTo(Sensor o) {
        return ComparisonChain.start()
                .compare(this.name, o.name)
                .compare(this.sensorType.toString(), o.sensorType.toString())
                .compare(this.sensorId, o.sensorId)
                .result();
    }
}
