package com.prakash.entity;

import com.prakash.repository.StudentRepository;

public class Student {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean verified;
    private double result;
    public static double examCutoff = 40.0;


    public Student() {

    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student(String name,String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Student(int id, String name, double result) {
        this.id = id;
        this.name = name;
        this.result = result;
    }


    public Student(int id,String name,String email,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public Student(int id, String name, String email, String password, boolean verified, double result) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.result = result;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }


    public static double getExamCutoff() {
        return examCutoff;
    }

    public static void setExamCutoff(double examCutoff) {
        Student.examCutoff = examCutoff;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
