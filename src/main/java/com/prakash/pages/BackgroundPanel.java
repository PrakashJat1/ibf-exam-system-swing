package com.prakash.pages;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructor to load the image
    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    // Override paintComponent to draw the background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}