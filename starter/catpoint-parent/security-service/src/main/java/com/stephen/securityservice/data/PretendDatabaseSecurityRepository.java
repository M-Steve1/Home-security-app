package com.stephen.securityservice.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.Preferences;

/**
 * Fake repository implementation for demo purposes. Stores state information in local
 * memory and writes it to user preferences between app loads. This implementation is
 * intentionally a little hard to use in unit tests, so watch out!
 */
public class PretendDatabaseSecurityRepository implements SecurityRepository{
    private Set<Sensor> sensors;
    private AlarmStatus alarmStatus;
    private ArmingStatus armingStatus;
    private boolean catStatus;

    private static final String SENSORS = "SENSOR";
    private static final String ALARM_STATUS = "ALARM_STATUS";
    private static final String ARMING_STATUS = "ARMING_STATUS";

    private Preferences prefs = Preferences.userNodeForPackage(PretendDatabaseSecurityRepository.class);
    private Gson gson = new Gson();

    public PretendDatabaseSecurityRepository() {
        alarmStatus = AlarmStatus.valueOf(prefs.get(ALARM_STATUS, AlarmStatus.NO_ALARM.toString()));
        armingStatus = ArmingStatus.valueOf(prefs.get(ARMING_STATUS, ArmingStatus.DISARMED.toString()));

        String sensorString = prefs.get(SENSORS, null);
        if(sensorString == null) {
            sensors = new TreeSet<>();
        } else {
            Type type = new TypeToken<Set<Sensor>>(){}.getType();
            sensors = gson.fromJson(sensorString, type);
        }
    }

    @Override
    public void updateSensor(Sensor sensor) {
        sensors.remove(sensor);
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void removeSensor(Sensor sensor) {
        sensors.remove(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void setAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
        prefs.put(ALARM_STATUS, this.alarmStatus.toString());
    }

    @Override
    public void setArmingStatus(ArmingStatus armingStatus) {
        this.armingStatus = armingStatus;
        prefs.put(ARMING_STATUS, this.armingStatus.toString());
    }

    @Override
    public void setCatStatus(boolean catStatus) {
        this.catStatus = catStatus;
    }

    @Override
    public Set<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    @Override
    public ArmingStatus getArmingStatus() {
        return armingStatus;
    }

    @Override
    public boolean getCatStatus() {
        return catStatus;
    }
}
