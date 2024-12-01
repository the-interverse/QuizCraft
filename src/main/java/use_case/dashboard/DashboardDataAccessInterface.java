package use_case.dashboard;

import java.util.List;
import java.util.Map;

/**
 * DAO for the Login Use Case.
 */
public interface DashboardDataAccessInterface {
    /**
     * Returns the list of quizzes created by user.
     * @param username username
     * @return the list of quizzes created by user.
     */
    List<String> getQuizzes(String username);

    /**
     * Returns the list of quizzes created by user.
     * @param username username
     * @param quizName quiz naem
     * @return quiz data
     */
    List<Map<String, Object>> getQuizData(String username, String quizName);
}

