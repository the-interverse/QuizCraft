package use_case.create_quiz;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface CreateQuizInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(CreateQuizInputData loginInputData);
}
