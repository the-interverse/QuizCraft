package use_case.dashboard;

import entity.Quiz;
import java.util.List;

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
        List<Quiz> quizzes = dashboardDataAccessObject.getQuizzes();
        if (quizzes.isEmpty()) {
            dashboardPresenter.prepareFailView("No quizzes found.");
        } else {
            final DashboardOutputData outputData = new DashboardOutputData(quizzes);
            dashboardPresenter.prepareSuccessView(outputData);
        }

    }
    @Override
    public void switchToCreateQuizView() {dashboardPresenter.switchToCreateQuizView();}

}