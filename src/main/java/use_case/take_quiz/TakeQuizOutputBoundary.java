package use_case.take_quiz;

/**
 * Output Boundary for actions that are related to taking a quiz
 */
public interface TakeQuizOutputBoundary {

    /**
     * Prepares the success view for taking the quiz use case
     * @param takeQuizOutputData output data
     */
    void prepareSuccessView(TakeQuizOutputData takeQuizOutputData);

    /**
     * Prepares the fail view for taking the quiz use case
     * @param error error message
     */
    void prepareFailView(String error);
}
