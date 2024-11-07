package use_case.create_quiz;

/**
 * The output boundary for the Login Use Case.
 */
public interface CreateQuizOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateQuizOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToDashboardView();

}
