package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

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
    public void execute(String username) {
        final DashboardInputData dashboardInputData = new DashboardInputData(username);
        dashboardInteractor.execute(dashboardInputData);
    }
    public void switchToCreateQuizView() {
        viewManagerModel.setState("createQuiz");
        viewManagerModel.firePropertyChanged();
    }
}

