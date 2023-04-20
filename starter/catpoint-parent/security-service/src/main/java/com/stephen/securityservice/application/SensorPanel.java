package com.stephen.securityservice.application;

import com.stephen.securityservice.SecurityService;
import com.stephen.securityservice.StyleService;
import com.stephen.securityservice.data.AlarmStatus;
import com.stephen.securityservice.data.Sensor;
import com.stephen.securityservice.data.SensorType;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SensorPanel extends JPanel implements StatusListener {
    private SecurityService securityService;

    JLabel paneLabel = new JLabel("Sensor Management");
    JLabel newSenorName = new JLabel("Name:");
    JLabel newSensorType = new JLabel("Sensor Type:");
    JTextField newSensorNameField = new JTextField();
    JComboBox dropdown = new JComboBox(SensorType.values());
    JButton addNewSensorButton = new JButton("Add New Sensor");

    JPanel newSensorPanel;
    JPanel sensorList;

    public SensorPanel(SecurityService securityService) {
        super();
        setLayout(new MigLayout());
        this.securityService = securityService;
        securityService.addStatusListener(this);

        paneLabel.setFont(StyleService.HEADING_FONT);

        addNewSensorButton.addActionListener(e -> {
            addSensor(new Sensor(newSensorNameField.getText(), SensorType.valueOf(dropdown.getSelectedItem().toString())));
        });


        newSensorPanel = buildAddSensorPanel();
        sensorList = new JPanel();
        sensorList.setLayout(new MigLayout());
        updateSensorList(sensorList);

        add(paneLabel, "wrap");
        add(newSensorPanel, "span");
        add(sensorList, "span");
    }

    private JPanel buildAddSensorPanel() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout());
        p.add(newSenorName);
        p.add(newSensorNameField, "width 50:100:200");
        p.add(newSensorType);
        p.add(dropdown, "wrap");
        p.add(addNewSensorButton, "span 3");
        return  p;
    }

    private  void updateSensorList(JPanel p) {
        p.removeAll();
        securityService.getSensors().stream().forEach(s -> {
            JLabel sensorLabel = new JLabel(String.format("%s(%s): %s", s.getName(), s.getSensorType().toString(), s.getActive() ? "Active": "Inactive"));
            JButton sensorToggleButton = new JButton(s.getActive() ? "Deactivate":"Activate");
            JButton removeSensorButton = new JButton("Remove Sensor");

            sensorToggleButton.addActionListener(e -> setSensorActivity(s, !s.getActive()));
            removeSensorButton.addActionListener(e -> removeSensor(s));

            p.add(sensorLabel, "width 300:300:300");
            p.add(sensorToggleButton, "width 100:100:100");
            p.add(removeSensorButton, "wrap");
        });

        repaint();
        revalidate();
    }

    /**
     * securityService changes the sensor activation status and update the sensor list
     * @param sensor to change activation status
     * @param isActive current activation status
     */
    private void setSensorActivity(Sensor sensor, boolean isActive) {
        securityService.changeSensorActivationStatus(sensor, isActive);
        updateSensorList(sensorList);
    }

    /**
     * Add a sensor to securityService and then rebuild sensor list
     * @param sensor to add
     */
    private void addSensor(Sensor sensor) {
        securityService.addSensor(sensor);
        updateSensorList(sensorList);
    }

    /**
     * Remove a sensor from the securityService and then rebuild the sensor list
     * @param sensor The sensor to remove
     */
    private void removeSensor(Sensor sensor) {
        securityService.removeSensor(sensor);
        updateSensorList(sensorList);
    }
    @Override
    public void notify(AlarmStatus status) {

    }

    @Override
    public void catDetected(boolean catDetected) {

    }

    @Override
    public void sensorStatusChanged() {
        updateSensorList(sensorList);
    }
}
