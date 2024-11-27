package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

import java.util.List;

/**
 * The Controller for the Dashboard Use Case.
 */
public class DashboardController {

    private final DashboardInputBoundary dashboardInteractor;
    private final ViewManagerModel viewManagerModel;

    public DashboardController(DashboardInputBoundary dashboardInteractor, ViewManagerModel viewManagerModel) {
        this.dashboardInteractor = dashboardInteractor;
        this.viewManagerModel = viewManagerModel;
    }
    public void execute(String username, List<String> quizzes) {
        final DashboardInputData dashboardInputData = new DashboardInputData(username, quizzes);
        dashboardInteractor.execute(dashboardInputData);
    }
    public void switchToCreateQuizView() {
        dashboardInteractor.switchToCreateQuizView();
    }
    public void switchToViewQuizView(String username, String quizName) {
        System.out.println("DashboardController: Viewing quiz " + quizName);
        dashboardInteractor.switchToViewQuizView(username, quizName);
    }

}
