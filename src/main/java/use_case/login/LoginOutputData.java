package use_case.login;

import entity.Quiz;

import java.util.List;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final List<String> quizzes;

    public LoginOutputData(String username, boolean useCaseFailed, List<String> quizzes) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.quizzes = quizzes;
    }

    public String getUsername() {
        return username;
    }
    public List<String> getQuizzes() {return quizzes;}


}
