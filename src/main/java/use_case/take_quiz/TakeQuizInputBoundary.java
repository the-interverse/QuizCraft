package use_case.take_quiz;

/**
 * Input Boundary for actions that are related to taking a quiz
 */
public interface TakeQuizInputBoundary {
    /**
     * Executes take the quiz use case
     * @param takeQuizInputData the input data
     */
    void execute(TakeQuizInputData takeQuizInputData);
}
