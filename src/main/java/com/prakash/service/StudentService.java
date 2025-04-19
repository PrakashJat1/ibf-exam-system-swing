package com.prakash.service;

import com.prakash.entity.Student;
import com.prakash.repository.StudentRepository;

import java.sql.SQLException;
import java.util.List;

public class StudentService {

    StudentRepository studentRepository;
    public StudentService(){
            this.studentRepository = new StudentRepository();
    }

    public boolean updateStudentByEmail(String email,Student student){return studentRepository.updateStudentByEmail(email,student);}

    public Student getStudentByEmail(String email) {return  studentRepository.getStudentByEmail(email);}

    public boolean updateResult(Student student){ return studentRepository.updateResult(student);}

    public Student getStudent(String email,String password) {
        return studentRepository.getStudent(email,password);
    }

    public boolean verifyStudent(Student student){
        return studentRepository.verifyStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public boolean saveAllStudents(List<Student> students) {
        return studentRepository.saveAllStudents(students);
    }

    public boolean saveAllStudentsManually(List<Student> students) {
        return studentRepository.saveAllStudentsManually(students);
    }

    public List<Student> getAllStudentsResult() {
        return studentRepository.getAllStudentsResult();
    }

    public boolean deleteStudentByEmail(String email){return studentRepository.deleteStudentByEmail(email);}

    public boolean deleteAllStudent(){return studentRepository.deleteAllStudent();}


}
