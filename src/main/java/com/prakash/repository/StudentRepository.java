package com.prakash.repository;

import com.prakash.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    String url = "jdbc:mysql://localhost:3306/ibonlineexaminationsystem?useSSL=false&serverTimezone=UTC";
    String username = "root";
    String password = "prakash@123";


    public StudentRepository(){
    }

    public Connection getConnetion()  {

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(url,username,password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Connection con = getConnetion();

    Statement statement;

    {
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //updateResult
    public boolean updateResult(Student student) {
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE STUDENTS SET RESULT = ? WHERE email = ? AND password = ?");
            pst.setDouble(1,student.getResult());
            pst.setString(2,student.getEmail());
            pst.setString(3,student.getPassword());

           return  pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    //Update by email
    public boolean updateStudentByEmail(String email,Student student)
    {
        try {
            PreparedStatement pst =  con.prepareStatement("UPDATE students SET name = ?, email = ?,password = ?,verified = ? WHERE email = ?");
            pst.setString(1,student.getName());
            pst.setString(2,student.getEmail());
            pst.setString(3,student.getPassword());
            pst.setBoolean(4,student.isVerified());
            pst.setString(5,email);

            return  pst.executeUpdate() > 0;

        } catch (SQLException e) {

            return false;
        }

    }

    //get by email
    public Student getStudentByEmail(String email) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM STUDENTS WHERE email = ?");
            pst.setString(1,email);

            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                return new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBoolean(5),rs.getDouble(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    //get A Student
    public Student getStudent(String email,String password) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM STUDENTS WHERE email = ? AND password = ?");
            pst.setString(1,email);
            pst.setString(2,password);

            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                return new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBoolean(5),rs.getDouble(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //verifyStudent
    public boolean verifyStudent(Student student)  {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM STUDENTS WHERE email = '"+student.getEmail()+"' AND password = '"+student.getPassword()+"'");

            if (rs.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    getAllStudents
    public List<Student> getAllStudents() {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENTS ORDER BY id");
            List<Student> students = new ArrayList<>();
            while (rs.next())
            {
                students.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //    getAllStudents
    public List<Student> getAllStudentsResult() {
        try {
            ResultSet rs = statement.executeQuery("SELECT STUDENTS.id,STUDENTS.name,STUDENTS.result FROM STUDENTS ORDER BY id");
            List<Student> students = new ArrayList<>();
            while (rs.next())
            {
                students.add(new Student(rs.getInt(1),rs.getString(2),rs.getDouble(3)));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Save All Students by excel file
    public boolean saveAllStudents(List<Student> students) {
        try {
            for (int i = 0; i < students.size(); i++) {
                PreparedStatement pst = con.prepareStatement("INSERT INTO STUDENTS(ID,NAME,EMAIL,PASSWORD,verified) VALUES(?,?,?,?,?)");
                Student student = students.get(i);

                pst.setInt(1,student.getId());
                pst.setString(2,student.getName());
                pst.setString(3,student.getEmail());
                pst.setString(4,student.getPassword());
                pst.setBoolean(5,student.isVerified());

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            return false;
        }
        return  true;
    }


    //save all students manually
    public boolean saveAllStudentsManually(List<Student> students) {
        try {
            for (int i = 0; i < students.size(); i++) {
                PreparedStatement pst = con.prepareStatement("INSERT INTO STUDENTS(NAME,EMAIL,PASSWORD,verified) VALUES(?,?,?,?)");
                Student student = students.get(i);

                pst.setString(1,student.getName());
                pst.setString(2,student.getEmail());
                pst.setString(3,student.getPassword());
                pst.setBoolean(4,true);

                pst.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return  false;
    }

    //Delete Student
    public boolean deleteStudentByEmail(String email) {
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM Students WHERE email = ?");
            pst.setString(1,email);

            return pst.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            return false;
        }


    }


    public boolean deleteAllStudent()
    {
        try {
            PreparedStatement pst = con.prepareStatement("DELETE  FROM students");
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
