package use_case.dashboard;

import entity.Quiz;
import java.util.List;

/**
 * DAO for the Login Use Case.
 */
public interface DashboardDataAccessInterface {
    /**
     * Returns the list of quizzes created by user.
     * @return the list of quizzes created by user.
     */
    List<String> getQuizzes(String username);
}

