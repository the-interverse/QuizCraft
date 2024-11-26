package use_case.view_quiz;

import java.util.List;
import java.util.Map;

/**
 * Output Data for the ViewQuiz Use Case.
 */
public class ViewQuizOutputData {


    private String quizName;
    private List<Map<String, Object>> questionsAndOptions;


    public ViewQuizOutputData(String quizName, List<Map<String, Object>> questionsAndOptions) {
        this.quizName = quizName;
        this.questionsAndOptions = questionsAndOptions;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<Map<String, Object>> getQuestionsAndOptions() {
        return questionsAndOptions;
    }

}
