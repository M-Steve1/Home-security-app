package com.stephen.securityservice.application;

import com.stephen.securityservice.SecurityService;
import com.stephen.securityservice.StyleService;
import com.stephen.securityservice.data.AlarmStatus;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements StatusListener {

    private JLabel cameraPanel;
    private JLabel imageDisplayLabel;
    private SecurityService securityService;
    private BufferedImage selectedImage;
    private int WIDTH = 300;
    private int HEIGHT = 255;

    public ImagePanel(SecurityService securityService) {
        super();
        setLayout(new MigLayout());
        this.securityService = securityService;
        securityService.addStatusListener(this);

        cameraPanel = new JLabel("Camera Feed");
        cameraPanel .setFont(StyleService.HEADING_FONT);
        imageDisplayLabel = new JLabel();
        imageDisplayLabel.setBackground(Color.WHITE);
        imageDisplayLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        imageDisplayLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JButton addImageButton = new JButton("Refresh Camera");
        addImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("Select Picture");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            try {
                selectedImage = ImageIO.read(chooser.getSelectedFile());
                Image tmp = new ImageIcon(selectedImage).getImage();
                imageDisplayLabel.setIcon(new ImageIcon(tmp.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
            } catch (IOException | NullPointerException ioe) {
                JOptionPane.showMessageDialog(null, "Invalid Image Selected");
            }
            repaint();
        });

        JButton scanImageButton = new JButton("Scan Picture");
        scanImageButton.addActionListener(e -> {
            securityService.processImage(selectedImage);
        });

        add(cameraPanel, "span 3 wrap");
        add(imageDisplayLabel, "span 3, wrap");
        add(addImageButton);
        add(scanImageButton);
    }


    @Override
    public void notify(AlarmStatus status) {

    }

    @Override
    public void catDetected(boolean catDetected) {
        if (catDetected) {
            cameraPanel.setText("DANGER - CAT DETECTED");
        } else  {
            cameraPanel.setText("No Cats Detected");
        }
    }

    @Override
    public void sensorStatusChanged() {

    }
}
