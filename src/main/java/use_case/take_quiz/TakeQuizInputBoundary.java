package use_case.take_quiz;

import use_case.create_quiz.CreateQuizInputData;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface TakeQuizInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(CreateQuizInputData loginInputData);
}
