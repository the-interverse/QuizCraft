package use_case.dashboard;

import use_case.create_quiz.CreateQuizInputData;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface DashboardInputBoundary {

    /**
     * Executes the login use case.
     * @param DashboardInputData the input data
     */
    void execute(DashboardInputData DashboardInputData);
    void switchToCreateQuizView();

}


