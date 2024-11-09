package use_case.dashboard;

/**
 * The output boundary for the Login Use Case.
 */
public interface LoggedInOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoggedInOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToCreateQuizView();
}
