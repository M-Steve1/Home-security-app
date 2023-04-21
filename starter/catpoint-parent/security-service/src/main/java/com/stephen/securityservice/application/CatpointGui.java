package com.stephen.securityservice.application;

import com.stephen.imageservice.FakeImageService;
import com.stephen.imageservice.ImageService;
import com.stephen.securityservice.SecurityService;
import com.stephen.securityservice.data.PretendDatabaseSecurityRepository;
import com.stephen.securityservice.data.SecurityRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * This class extends JFrame and contains all the top level panels
 */
public class CatpointGui extends JFrame {
    private SecurityRepository securityRepository = new PretendDatabaseSecurityRepository();
    /**
     * Replace FakeImageService with AwsImageService if you have your profile and config properties
     * set up.
     */
    private ImageService imageService = new FakeImageService();
    private SecurityService securityService = new SecurityService(securityRepository, imageService);
    private DisplayPanel displayPanel = new DisplayPanel(securityService);
    private ImagePanel imagePanel = new ImagePanel(securityService);
    private ControlPanel controlPanel = new ControlPanel(securityService);
    private SensorPanel sensorPanel = new SensorPanel(securityService);

    public CatpointGui() {
        setLocation(100, 100);
        setSize(600, 850);
        setTitle("Very Secure Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel, "wrap");

        getContentPane().add(mainPanel);
    }
}
