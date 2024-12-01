package use_case.dashboard;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface DashboardInputBoundary {

    /**
     * Executes the login use case.
     * @param DashboardInputData the input data
     */
    void execute(DashboardInputData DashboardInputData);

    /**
     * Executes the login use case.
     *
     */

    void switchToCreateQuizView();
    /**
     * Executes the login use case.
     * @param username the input data
     * @param quizName quiz name
     */

    void switchToViewQuizView(String username, String quizName);
}
