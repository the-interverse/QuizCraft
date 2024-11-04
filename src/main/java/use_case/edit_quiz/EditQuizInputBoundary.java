package use_case.edit_quiz;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface EditQuizInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(EditQuizInputData loginInputData);
}
