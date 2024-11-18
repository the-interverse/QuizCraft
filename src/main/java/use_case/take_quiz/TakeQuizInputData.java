package use_case.take_quiz;

import java.util.Map;

/**
 * The input data for the Take Quiz use case.
 */
public class TakeQuizInputData {

    private final Map<Integer, Character> givenAnswers;
    private final String username;
    private final String quizName;

    public TakeQuizInputData(Map<Integer, Character> givenAnswers, String username, String quizName) {
        this.givenAnswers = givenAnswers;
        this.username = username;
        this.quizName = quizName;
    }

    public Map<Integer, Character> getGivenAnswers() {
        return givenAnswers;
    }

    public String getUsername() {
        return username;
    }

    public String getQuizName() {
        return quizName;
    }
}
