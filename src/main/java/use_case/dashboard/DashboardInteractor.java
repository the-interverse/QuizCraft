package use_case.dashboard;

import entity.Quiz;
import java.util.List;
import java.util.Map;

/**
 * The Dashboard Interactor.
 */
public class DashboardInteractor implements DashboardInputBoundary {

    private final DashboardDataAccessInterface dashboardDataAccessObject;
    private final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface dashboardDataAccessInterface,
                               DashboardOutputBoundary dashboardOutputBoundary) {
        this.dashboardDataAccessObject = dashboardDataAccessInterface;
        this.dashboardPresenter = dashboardOutputBoundary;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        String username = dashboardInputData.getUsername();
        if (username == null || username.isEmpty()) {
            dashboardPresenter.prepareFailView("Invalid username.");
            return;
        }
        List<String> quizzes = dashboardDataAccessObject.getQuizzes(dashboardInputData.getUsername());
        if (quizzes.isEmpty()) {
            dashboardPresenter.prepareFailView("No quizzes found.");
        } else {
            final DashboardOutputData outputData = new DashboardOutputData(quizzes);
            dashboardPresenter.prepareSuccessView(outputData);
        }

    }
    @Override
    public void switchToCreateQuizView() {
        dashboardPresenter.switchToCreateQuizView();
    }

    @Override
    public void switchToViewQuizView(String username, String quizName) {
        List<Map<String, Object>> quizData = dashboardDataAccessObject.getQuizData(username, quizName);
        dashboardPresenter.switchToViewQuizView(quizData, quizName);
    }

}