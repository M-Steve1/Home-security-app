package com.stephen.securityservice.data;

import java.util.Set;

public interface SecurityRepository {
    void updateSensor(Sensor sensor);
    void addSensor(Sensor sensor);
    void removeSensor(Sensor sensor);
    void setAlarmStatus(AlarmStatus alarmStatus);
    void setArmingStatus(ArmingStatus armingStatus);
    void setCatStatus(boolean catStatus);
    Set<Sensor> getSensors();
    AlarmStatus getAlarmStatus();
    ArmingStatus getArmingStatus();
    boolean getCatStatus();
}
