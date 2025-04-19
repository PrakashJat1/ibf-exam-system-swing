package com.prakash;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.prakash.controller.StudentController;
import com.prakash.entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class LoginPage {


    // Resize Icon Method
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }


    public static void main(String[] args) throws SQLException {
        FlatMacLightLaf.setup();

        // Font
        Font f = new Font("SansSerif", Font.BOLD, 14);

        // Create Frame
        JFrame frame = new JFrame("Login Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // ImageIcon
        ImageIcon themeIcon = resizeIcon(new ImageIcon("src/image/LigthMode.png"), 30, 30);

        // Theme Toggle Button
        JToggleButton themeButton = new JToggleButton(themeIcon);
//        themeButton.setPreferredSize(new Dimension(40, 40));
        themeButton.putClientProperty("JButton.buttonType","roundRect");

        themeButton.addActionListener(e -> {
            if (themeButton.isSelected()) {
                    FlatMacDarkLaf.setup();
                    themeButton.setIcon(resizeIcon(new ImageIcon("src/image/DarkMode.png"), 30, 30));

            } else {
                FlatMacLightLaf.setup();
                themeButton.setIcon(resizeIcon(new ImageIcon("src/image/LigthMode.png"), 30, 30));
            }
            SwingUtilities.updateComponentTreeUI(frame);
        });

        // Create Panel for Login
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));
        panel.putClientProperty("JPanel.background",UIManager.getColor("Panel.background"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        // Components
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(f);
        JTextField userText = new JTextField(15);
        userText.putClientProperty("JComponent.roundRect",true);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(f);
        JPasswordField passText = new JPasswordField(15);
        passText.putClientProperty("JComponent.roundRect",true);

        JButton loginButton = new JButton("Login");
        loginButton.putClientProperty("JButton.buttonType","roundRect");

        JLabel register = new JLabel("<html><u> Register Here </u></html>");



        // Add Components to Panel
        panel.add(userLabel, gbc);
        gbc.gridy++;
        panel.add(userText, gbc);
        gbc.gridy++;
        panel.add(passLabel, gbc);
        gbc.gridy++;
        panel.add(passText, gbc);
        gbc.gridy++;
        panel.add(loginButton, gbc);

        // Add Panel to Frame in Center
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 1.0;
        gbcPanel.weighty = 1.0;
        gbcPanel.fill = GridBagConstraints.NONE;
        gbcPanel.insets = new Insets(30,5,5,5);
        frame.add(panel, gbcPanel);

        // Add Theme Button to Top-Right
        GridBagConstraints gbcTheme = new GridBagConstraints();
        gbcTheme.gridx = 1;
        gbcTheme.gridy = 0;
        gbcTheme.anchor = GridBagConstraints.NORTHEAST;
        gbcTheme.insets = new Insets(10, 10, 0, 10);
        frame.add(themeButton, gbcTheme);




        // Add Register Button Below the Login Panel
        GridBagConstraints gbcRegister = new GridBagConstraints();
        gbcRegister.gridx = 0; // Same column as Login
        gbcRegister.gridy = 1; // Below the Login Panel
        gbcRegister.weightx = 1.0; // Span across two columns for centering
        gbcRegister.insets = new Insets(10, 5, 200, 5); // Add some spacing from login
        gbcRegister.anchor = GridBagConstraints.CENTER; // Center alignment

        gbc.gridy++;
        panel.add(register,gbc);

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);



            register.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(frame,"Register","Success",JOptionPane.INFORMATION_MESSAGE);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });


            StudentController studentController = new StudentController();
            //verify Student
            loginButton.addActionListener(e -> {
                Student student = new Student();
                student.setEmail(userText.getText());
                student.setPassword(new String(passText.getPassword()));


                try {
                    if(studentController.verifyStudent(student))
                    {
                        JOptionPane.showMessageDialog(frame,"Login Succesfully...!","Success",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame,"Invalid Username or Password","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
}

    }

