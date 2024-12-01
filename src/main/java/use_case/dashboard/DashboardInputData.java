package use_case.dashboard;

import java.util.List;

/**
 * The Input Data for the Dashboard Use Case.
 */
public class DashboardInputData {

    private final String username;
    private final List<String> quizzes;

    public DashboardInputData(String username, List<String> quizzes) {
        this.username = username;
        this.quizzes = quizzes;
    }

    String getUsername() {
        return username;
    }

    List<String> getQuizzes() {
        return quizzes;
    }

}
