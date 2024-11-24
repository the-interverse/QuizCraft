package use_case.dashboard;

import entity.Quiz;

import java.util.List;

/**
 * The Output Data for the Dashboard Use Case.
 */
public class DashboardOutputData {

    private final List<Quiz> quizzes;

    public DashboardOutputData(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}

