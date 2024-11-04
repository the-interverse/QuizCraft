package use_case.take_quiz;

/**
 * The output boundary for the Login Use Case.
 */
public interface TakeQuizOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(TakeQuizOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
