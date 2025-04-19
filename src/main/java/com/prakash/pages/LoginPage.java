package com.prakash.pages;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.prakash.controller.StudentController;
import com.prakash.entity.User;
import com.prakash.controller.UserController;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

public class LoginPage {


    //mode button
    public static JToggleButton modeButton(JFrame frame) {
        // ImageIcon
        ImageIcon themeIcon = resizeIcon(new ImageIcon("src/image/LigthMode.png"), 30, 30);

        // Theme Toggle Button
        JToggleButton themeButton = new JToggleButton(themeIcon);
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

        return themeButton;

    }


    // Resize Icon Method
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }


   public LoginPage(JFrame frame){


       JPanel mainPanel = new JPanel(new BorderLayout());
        FlatMacLightLaf.setup();

        // Font
        Font f = new Font("SansSerif", Font.BOLD, 14);

       // 🔘 Mode Button Panel (Top)
       JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Aligns to the right
       JToggleButton modeButton = modeButton(frame);
       modePanel.add(modeButton);


       // 🎨 Center Panel (Contains Logo, Label & Login Form)
       JPanel centerPanel = new JPanel(new BorderLayout());

       // 🖼️ Logo Panel
       JPanel logoPanel = new JPanel(new GridBagLayout());
       ImageIcon logo = resizeIcon(new ImageIcon("src/image/IBlogo.png"), 100, 100);
       JLabel logoLabel = new JLabel(logo);
       JLabel title = new JLabel("IB  ONLINE  EXAMINATION  SYSTEM", SwingConstants.CENTER);
       title.setFont(new Font("Serif", Font.BOLD, 24)); //
       title.setForeground(new Color(30, 144, 255)); //
       title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

       // Add MouseListener for clicking
       logoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
       logoLabel.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               try {
                   Desktop.getDesktop().browse(new URI("https://www.infobeans.com/infobeans-foundation/")); // Replace with your URL
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
           }
       });
       // Align Logo & Title in Center
       logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
       title.setAlignmentX(Component.CENTER_ALIGNMENT);
       logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
       logoPanel.add(logoLabel);
       logoPanel.add(title);

       // Create Panel for Login
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(500, 400));
       TitledBorder border = BorderFactory.createTitledBorder("Login");
       border.setTitleFont(f);
        panel.setBorder(border);
        panel.putClientProperty("JPanel.background",UIManager.getColor("Panel.background"));


        // Components
        JLabel userLabel = new JLabel("Email:");
        userLabel.setFont(f);
        JTextField userText = new JTextField(15);
        userText.putClientProperty("JComponent.roundRect",true);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(f);
        JPasswordField passText = new JPasswordField(15);
        passText.putClientProperty("JComponent.roundRect",true);

        JButton loginButton = new JButton("Login");
        loginButton.putClientProperty("JButton.buttonType","roundRect");
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(f);

        JLabel register = new JLabel("<html><u> Register Here </u></html>");
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.setForeground(Color.BLUE);
        register.setFont(f);

        // Add Components to Panel
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.weightx = 1.0;
       gbc.weighty = 1.0;
       gbc.insets = new Insets(5, 5, 5, 5);
       gbc.fill = GridBagConstraints.NONE;

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



       JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
       wrapperPanel.add(panel);

       JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       JToggleButton themeBut = modeButton(frame);
       themePanel.add(themeBut);




        // Add Register Button Below the Login Panel
        GridBagConstraints gbcRegister = new GridBagConstraints();
        gbcRegister.gridx = 0; // Same column as Login
        gbcRegister.gridy = 1; // Below the Login Panel
        gbcRegister.weightx = 1.0; // Span across two columns for centering
        gbcRegister.insets = new Insets(10, 5, 200, 5); // Add some spacing from login
        gbcRegister.anchor = GridBagConstraints.CENTER; // Center alignment

        gbc.gridy++;
        panel.add(register,gbc);


       centerPanel.add(logoPanel,BorderLayout.NORTH);
       centerPanel.add(wrapperPanel,BorderLayout.CENTER);

       mainPanel.add(modePanel,BorderLayout.NORTH);
       mainPanel.add(centerPanel,BorderLayout.CENTER);

//       frame.setContentPane(new BackgroundPanel("src/image/IBlogo.png"));
       frame.add(mainPanel);
       frame.setLocationRelativeTo(null); // Center the frame
       frame.setVisible(true);



        //Register
       register.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.remove(mainPanel);
                    new RegisterPage(frame);
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


        //verify Student
        loginButton.addActionListener(e -> {
            User user = new User();
                user.setEmail(userText.getText());
                user.setPassword(new String(passText.getPassword()));

                //user decide
                String role = new UserController().verifyUser(user);
                if (role.equalsIgnoreCase("STUDENT")) {
                        frame.dispose();
                        new StudentDashboard(new StudentController().getStudent(user.getEmail(), user.getPassword()));
                    }
                    else if (role.equalsIgnoreCase("ADMIN"))
                    {
                        frame.remove(mainPanel);
                        new AdminDashboard(frame);
                    }
                    else if(role.equalsIgnoreCase("UNAUTHORIZED STUDENT :("))
                    JOptionPane.showMessageDialog(frame,"UNAUTHORIZED STUDENT :(","Error",JOptionPane.ERROR_MESSAGE);
                    else
                    JOptionPane.showMessageDialog(frame,"Please Register First..!","Error",JOptionPane.ERROR_MESSAGE);
            });
}
}


