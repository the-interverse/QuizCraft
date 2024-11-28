package use_case.create_quiz;
import java.io.File;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface CreateQuizInputBoundary {

    /**
     * Executes the login use case.
     * @param CreateQuizInputData the input data
     */
    void execute(CreateQuizInputData CreateQuizInputData);

    void switchToDashboardView(String username);

}
