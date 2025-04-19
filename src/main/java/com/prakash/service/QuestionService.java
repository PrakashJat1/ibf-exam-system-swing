package com.prakash.service;

import com.prakash.entity.Question;
import com.prakash.repository.QuestionRepository;

import java.util.List;

public class QuestionService {

    private QuestionRepository questionRepository;
    public QuestionService(){
        this.questionRepository = new QuestionRepository();
    }

    //get all question by section
    public List<Question> getAllQuestionsBySection(String section){
        return questionRepository.getAllQuestionsBySection(section);
    }

    public boolean saveAllQuestions(List<Question> questions){return questionRepository.saveAllQuestions(questions);}

    public void deleteSelectedQuestions(List<String> questions){ questionRepository.deleteSelectedQuestions(questions);}

    public boolean deleteAllQuestions(){return questionRepository.deleteAllQuestions();}
}
