package com.prakash.repository;

import com.prakash.entity.Question;
import org.w3c.dom.ls.LSInput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private String url = "jdbc:mysql://localhost:3306/ibonlineexaminationsystem";
    private String username = "root";
    private String password = "prakash@123";



    private Connection getConnection()
    {
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Connection con = getConnection();
    PreparedStatement pst = null;

    //get all question by section
    public List<Question> getAllQuestionsBySection(String section) {
        try {
            pst = con.prepareStatement("SELECT * FROM QUESTIONS WHERE SECTION = ? ORDER BY id");
            pst.setString(1,section);

            ResultSet rs = pst.executeQuery();
            List<Question> questions = new ArrayList<>();
            while (rs.next()){
                questions.add(new Question(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }

            return questions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //delete all questions
    public boolean deleteAllQuestions() {
        try {
            pst = con.prepareStatement("DELETE FROM Questions");
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

     public void deleteSelectedQuestions(List<String> questions) {
            try {
                for (String q : questions)
                {
                    pst = con.prepareStatement("DELETE FROM Questions WHERE  question = ?");
                    pst.setString(1,q);
                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    //save all questions
    public boolean saveAllQuestions(List<Question> questions){

        for (Question q : questions){

            try {
                PreparedStatement pst = con.prepareStatement("INSERT INTO Questions(question,option_a,option_b,option_c,option_d,correct_answer,section) values(?,?,?,?,?,?,?)");
                pst.setString(1,q.getQuestion());
                pst.setString(2,q.getOption_A());
                pst.setString(3,q.getOption_B());
                pst.setString(4,q.getOption_C());
                pst.setString(5,q.getOption_D());
                pst.setString(6,q.getCorrect_answer());
                pst.setString(7,q.getSection());

                pst.executeUpdate();

            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }


}
