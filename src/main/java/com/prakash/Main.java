package com.prakash;
import com.prakash.pages.AdminDashboard;
import com.prakash.pages.LoginPage;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Create Frame
        JFrame frame = new JFrame("Login Page");
        frame.setSize(1600, 850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        new LoginPage(frame);
//        new RegisterPage(frame);
//        new AdminDashboard(frame);
//        new StudentDashboard(new Student("student100@example.com","Pass@100"));
    }
}
