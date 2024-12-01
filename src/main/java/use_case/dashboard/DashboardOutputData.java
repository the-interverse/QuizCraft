package use_case.dashboard;

import java.util.List;

/**
 * The Output Data for the Dashboard Use Case.
 */
public class DashboardOutputData {

    private final List<String> quizzes;

    public DashboardOutputData(List<String> quizzes) {
        this.quizzes = quizzes;
    }

    public List<String> getQuizzes() {
        return quizzes;
    }
}

