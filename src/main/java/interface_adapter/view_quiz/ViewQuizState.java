package interface_adapter.view_quiz;

import java.util.*;

public class ViewQuizState {
    private String quizName = "Default Quiz";
    private String errorMessage;
    private List<Map<String, List<String>>> questionsAndOptions = new ArrayList<>();

    public ViewQuizState() {
        Map<String, List<String>> question1 = new HashMap<>();
        question1.put("Question 1", Arrays.asList("a", "b", "c", "d"));
        Map<String, List<String>> question2 = new HashMap<>();
        question2.put("Question 2", Arrays.asList("a", "b", "c", "d"));
        this.questionsAndOptions.add(question1);
        this.questionsAndOptions.add(question2);
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setQuizQuestionsAndOptions(List<Map<String, List<String>>> questionsAndOptions) {
        this.questionsAndOptions = questionsAndOptions;
    }

    public List<Map<String, List<String>>> getQuizQuestionsAndOptions() {
        return questionsAndOptions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
