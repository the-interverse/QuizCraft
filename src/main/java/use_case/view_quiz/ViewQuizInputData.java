package use_case.view_quiz;

import java.util.List;
import java.util.Map;

public class ViewQuizInputData {
    private String quizName;
    private List<Map<String, Object>> questionsAndOptions;
    private String username;

    public ViewQuizInputData(List<Map<String, Object>> questionsAndOptions, String username, String quizName) {
        this.quizName = quizName;
        this.questionsAndOptions = questionsAndOptions;
        this.username = username;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getUsername() {
        return username;
    }

    public List<Map<String, Object>> getQuestionsAndOptions() {
        return questionsAndOptions;
    }
}
