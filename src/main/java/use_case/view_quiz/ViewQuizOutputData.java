package use_case.view_quiz;
import entity.Quiz;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Output Data for the ViewQuiz Use Case.
 */
public class ViewQuizOutputData {


    private String quizName;
    private List<Map<String, List<String>>> questionsAndOptions;


    public ViewQuizOutputData(String quizName, List<Map<String, List<String>>> questionsAndOptions) {
        this.quizName = quizName;
        this.questionsAndOptions = questionsAndOptions;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<Map<String, List<String>>> getQuestionsAndOptions() {
        return questionsAndOptions;
    }

}
