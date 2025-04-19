package com.prakash.pages;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.prakash.entity.User;
import com.prakash.controller.UserController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class RegisterPage {

    // Resize Icon Method
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }




public RegisterPage(JFrame frame)
{

    JPanel mainPanel = new JPanel(new BorderLayout());

    FlatMacLightLaf.setup();

    // Font
    Font f = new Font("SansSerif", Font.BOLD, 14);

    // ImageIcon
    ImageIcon themeIcon = resizeIcon(new ImageIcon("src/image/LigthMode.png"), 30, 30);

    // Add Theme Button to Top-Right
    GridBagConstraints gbcTheme = new GridBagConstraints();
    gbcTheme.gridx = 1;
    gbcTheme.anchor = GridBagConstraints.NORTHEAST;
    gbcTheme.insets = new Insets(10, 10, 0, 10);

    // Create Panel for Register
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setPreferredSize(new Dimension(500, 400));
    TitledBorder border = BorderFactory.createTitledBorder("Register");
    border.setTitleFont(f);
    panel.setBorder(border);
    panel.putClientProperty("JPanel.background",UIManager.getColor("Panel.background"));

    // Components
    JLabel userName = new JLabel("User Name:");
    userName.setFont(f);
    JTextField userText = new JTextField(15);
    userText.putClientProperty("JComponent.roundRect",true);

    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(f);
    JTextField emailText = new JTextField(15);
    emailText.putClientProperty("JComponent.roundRect",true);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setFont(f);
    JPasswordField passText = new JPasswordField(15);
    passText.putClientProperty("JComponent.roundRect",true);

    JLabel confirmPassLabel = new JLabel("Confirm Password:");
    confirmPassLabel.setFont(f);
    JPasswordField confirmPasstext = new JPasswordField(15);
    confirmPasstext.putClientProperty("JComponent.roundRect",true);

    JLabel roleLabel = new JLabel("Role:");
    roleLabel.setFont(f);
    JComboBox<String> roletext = new JComboBox<>(new String[]{"STUDENT","ADMIN"});
    roletext.putClientProperty("JComponent.roundRect",true);

    JButton registerButton = new JButton("Register");
    registerButton.putClientProperty("JButton.buttonType","roundRect");
    registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    registerButton.setFont(f);


    //add components to panel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.NONE;


    panel.add(userName,gbc);
    gbc.gridy++;
    panel.add(userText,gbc);
    gbc.gridy++;
    panel.add(emailLabel,gbc);
    gbc.gridy++;
    panel.add(emailText,gbc);
    gbc.gridy++;
    panel.add(passLabel,gbc);
    gbc.gridy++;
    panel.add(passText,gbc);
    gbc.gridy++;
    panel.add(confirmPassLabel,gbc);
    gbc.gridy++;
    panel.add(confirmPasstext,gbc);
    gbc.gridy++;
    panel.add(roleLabel,gbc);
    gbc.gridy++;
    panel.add(roletext,gbc);
    gbc.gridy++;
    panel.add(registerButton,gbc);
    gbc.gridy++;

    GridBagConstraints panelgbc = new GridBagConstraints();
    panelgbc.gridx = 0;
    panelgbc.gridy = 0;
    panelgbc.weightx = 1.0;
    panelgbc.weighty = 1.0;
    panelgbc.fill = GridBagConstraints.NONE;
    panelgbc.insets = new Insets(30,5,5,5);

    JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    themePanel.add(LoginPage.modeButton(frame));

    JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
    wrapperPanel.add(panel);

    mainPanel.add(themePanel,BorderLayout.NORTH);
    mainPanel.add(wrapperPanel,BorderLayout.CENTER);
    frame.add(mainPanel);

    frame.setLocationRelativeTo(null); // Center the frame
    frame.setVisible(true);


    registerButton.addActionListener(e -> {

        User user = new User();
        String username = userText.getText();
        String email = emailText.getText();
        String password = String.valueOf(passText.getPassword());
        String confirmPassword = String.valueOf(confirmPasstext.getPassword());

        if(username.length() < 3)
            JOptionPane.showMessageDialog(frame,"Name length must be greater than 3 ","Error",JOptionPane.ERROR_MESSAGE);

        else if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",email))
            JOptionPane.showMessageDialog(frame,"Invalid mail","Error",JOptionPane.ERROR_MESSAGE);

        else if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",password))
            JOptionPane.showMessageDialog(frame,"Password must be Strong","Error",JOptionPane.ERROR_MESSAGE);
        else if(!password.equals(confirmPassword))
            JOptionPane.showMessageDialog(frame,"Confirm password must be same","Error",JOptionPane.ERROR_MESSAGE);
        else {
            user.setName(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(roletext.getSelectedIndex() == 0 ? "STUDENT" : "ADMIN");
            if( ! new UserController().saveUser(user))
            {
                JOptionPane.showMessageDialog(frame,"Email must be unique for all students","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(frame,"Register Successfully","Success", JOptionPane.INFORMATION_MESSAGE);
            frame.remove(mainPanel);
            new LoginPage(frame); //redirect to login page

        }


    });



}
}
