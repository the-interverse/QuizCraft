package use_case.view_quiz;

/**
 * The output boundary for the ViewQuiz Use Case.
 */
public interface ViewQuizOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ViewQuizOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToDashboardView();

}
