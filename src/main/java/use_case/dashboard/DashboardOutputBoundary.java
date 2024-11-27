package use_case.dashboard;

import java.util.List;
import java.util.Map;

/**
 * The output boundary for the Login Use Case.
 */
public interface DashboardOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(DashboardOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
    void switchToCreateQuizView();
    void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName);
}

