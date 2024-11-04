package use_case.dashboard;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface DashboardInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(DashboardInputData loginInputData);
}
