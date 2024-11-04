package use_case.view_quiz;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface ViewQuizInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(ViewQuizInputData loginInputData);
}
