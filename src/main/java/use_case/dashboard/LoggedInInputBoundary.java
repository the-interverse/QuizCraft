package use_case.dashboard;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoggedInInputBoundary {

    void execute(LoggedInInputData loggedInInputData);
    void switchToCreateQuizView();

}


