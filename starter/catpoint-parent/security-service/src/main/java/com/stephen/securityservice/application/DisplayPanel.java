package com.stephen.securityservice.application;

import com.stephen.securityservice.SecurityService;
import com.stephen.securityservice.StyleService;
import com.stephen.securityservice.data.AlarmStatus;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DisplayPanel extends JPanel implements StatusListener {
    private JLabel currentSystemStatus;

    public DisplayPanel(SecurityService securityService) {
        super();
        setLayout(new MigLayout());
        securityService.addStatusListener(this);

        JLabel panelLabel = new JLabel("Home Security");
        JLabel systemStatusLabel = new JLabel("System Status:");
        currentSystemStatus = new JLabel();
        panelLabel.setFont((StyleService.HEADING_FONT));

        notify(securityService.getAlarmStatus());

        add(panelLabel, "span 2, wrap");
        add(systemStatusLabel);
        add(currentSystemStatus, "wrap");
    }
    @Override
    public void notify(AlarmStatus status) {
        currentSystemStatus.setText(status.getDescription());
        currentSystemStatus.setBackground(status.getColor());
        currentSystemStatus.setOpaque(true);
    }

    @Override
    public void catDetected(boolean catDetected) {

    }

    @Override
    public void sensorStatusChanged() {

    }
}
