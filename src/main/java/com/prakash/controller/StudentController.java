package com.prakash.controller;

import com.prakash.entity.Student;
import com.prakash.service.StudentService;

import java.sql.SQLException;
import java.util.List;

public class StudentController {

    StudentService studentService = new StudentService();


    public StudentController(){}

    public boolean updateStudentByEmail(String email,Student student){return studentService.updateStudentByEmail(email,student);}

    public Student getStudentByEmail(String email) { return  studentService.getStudentByEmail(email);}

    public boolean updateResult(Student student){ return studentService.updateResult(student);}

    public Student getStudent(String email,String password){return studentService.getStudent(email,password);}


    public boolean verifyStudent(Student student) {
        return  studentService.verifyStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    public boolean saveAllStudents(List<Student> students) {
        return studentService.saveAllStudents(students);
    }

    public boolean saveAllStudentsManually(List<Student> students){ return studentService.saveAllStudentsManually(students);}

    public List<Student> getAllStudentsResult() {
        return studentService.getAllStudentsResult();
    }

    public boolean deleteStudentByEmail(String email){return studentService.deleteStudentByEmail(email);}

    public boolean deleteAllStudent(){return studentService.deleteAllStudent();}


}
