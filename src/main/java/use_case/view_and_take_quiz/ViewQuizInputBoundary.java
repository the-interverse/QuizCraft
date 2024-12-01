package use_case.view_and_take_quiz;

/**
 * Input Boundary for actions which are related to ViewQuiz.
 */
public interface ViewQuizInputBoundary {

    /**
     * Executes the ViewQuiz use case.
     * @param ViewQuizInputData the input data
     */
    void execute(ViewQuizInputData ViewQuizInputData);

    void switchToDashboardView();

}
