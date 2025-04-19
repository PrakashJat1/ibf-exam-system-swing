package com.prakash.controller;

import com.prakash.entity.Question;
import com.prakash.service.QuestionService;

import java.util.List;

public class QuestionController {

    QuestionService questionService = new QuestionService();

    //get all question by section
    public List<Question> getAllQuestionsBySection(String section){
        return questionService.getAllQuestionsBySection(section);
    }

    public boolean saveAllQuestions(List<Question> questions){return questionService.saveAllQuestions(questions);}

    public void deleteSelectedQuestions(List<String> questions){questionService.deleteSelectedQuestions(questions);}

    public boolean deleteAllQuestions(){return questionService.deleteAllQuestions();}

}
