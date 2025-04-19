package com.prakash.repository;
import com.prakash.entity.User;

import java.sql.*;

public class UserRepository {

    String url = "jdbc:mysql://localhost:3306/IBONLINEEXAMINATIONSYSTEM";

    private Connection getConnection(){
        Connection con = null;
        try{
             con = DriverManager.getConnection(url,"root","prakash@123");
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return con;
    }

    Connection con = getConnection();


    //verify User
    public String verifyUser(User user){

        try {
            PreparedStatement pst = con.prepareStatement("SELECT role FROM users WHERE email = ? AND password = ?");
            pst.setString(1,user.getEmail());
            pst.setString(2,user.getPassword());

            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                if(rs.getString(1).equalsIgnoreCase("ADMIN"))
                return "ADMIN";
                else return "UNAUTHORIZED STUDENT :(";
            }

            PreparedStatement pst1 = con.prepareStatement("SELECT students.verified FROM students WHERE email = ? AND password = ?");
            pst1.setString(1,user.getEmail());
            pst1.setString(2,user.getPassword());
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                return "STUDENT";
            }

             return "";


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    //Save user
    public boolean saveUser(User user){

        try {


            if(user.getRole().equals("ADMIN"))
            {
                PreparedStatement pst = con.prepareStatement("INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)");
                pst.setString(1,user.getName());
                pst.setString(2,user.getEmail());
                pst.setString(3,user.getPassword());
                pst.setString(4,user.getRole().toUpperCase());

                return pst.executeUpdate() > 0;
            }
            else {
                PreparedStatement pst = con.prepareStatement("INSERT INTO students(name,email,password,verified) VALUES(?,?,?,?)");
                pst.setString(1,user.getName());
                pst.setString(2,user.getEmail());
                pst.setString(3,user.getPassword());
                pst.setBoolean(4,false);

                pst.executeUpdate();

                pst = con.prepareStatement("INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)");
                pst.setString(1,user.getName());
                pst.setString(2,user.getEmail());
                pst.setString(3,user.getPassword());
                pst.setString(4,user.getRole().toUpperCase());

                return pst.executeUpdate() > 0;
            }


        } catch (SQLException e) {
            return  false;
        }
    }



}
