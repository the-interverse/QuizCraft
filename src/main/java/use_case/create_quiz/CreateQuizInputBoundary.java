package use_case.create_quiz;

/**
 * Input Boundary for actions which are related to quiz creation.
 */
public interface CreateQuizInputBoundary {

    /**
     * Executes the Create Quiz use case.
     * @param createQuizInputData the input data
     */
    void execute(CreateQuizInputData createQuizInputData);
}
