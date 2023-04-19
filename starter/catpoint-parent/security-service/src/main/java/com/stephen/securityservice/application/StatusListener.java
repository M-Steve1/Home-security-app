package com.stephen.securityservice.application;

import com.stephen.securityservice.data.AlarmStatus;

/**
 * Identifies the component that should be notified whenever the system status is changed.
 */
public interface StatusListener {
    void notify(AlarmStatus status);
    void catDetected(boolean catDetected);
    void sensorStatusChanged();
}
