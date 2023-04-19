package com.stephen.securityservice.application;

import com.stephen.securityservice.SecurityService;
import com.stephen.securityservice.StyleService;
import com.stephen.securityservice.data.ArmingStatus;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains buttons that manipulate the arming status
 */

public class ControlPanel extends JPanel {
    private SecurityService securityService;
    private Map<ArmingStatus, JButton>  buttonMap;

    public ControlPanel(SecurityService securityService) {
        super();
        setLayout(new MigLayout());
        this.securityService = securityService;

        JLabel panelLabel = new JLabel("System Control");
        panelLabel.setFont(StyleService.HEADING_FONT);
        add(panelLabel, "span 3, wrap");

        buttonMap = Arrays.stream(ArmingStatus.values()).collect(Collectors.toMap(status -> status, status -> new JButton(status.getDescription())));

        buttonMap.forEach((k, v) -> {
            v.addActionListener(e -> {
                securityService.setArmingStatus(k);
                buttonMap.forEach((status, button) -> {
                    button.setBackground(status == k ? status.getColor(): null);
                });
            });
        });

        Arrays.stream(ArmingStatus.values()).forEach(status -> {
            add(buttonMap.get(status));
        });

        ArmingStatus currentStatus = securityService.getArmingStatus();
        buttonMap.get(currentStatus).setBackground(currentStatus.getColor());
    }
}
