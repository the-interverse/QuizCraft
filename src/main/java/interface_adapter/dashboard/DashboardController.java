package interface_adapter.dashboard;

import java.util.List;

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

    /**
     * Executes the dashboard use case with the given username and quizzes.
     * @param username The username of the current user. Must not be null.
     * @param quizzes A list of quiz names associated with the user. Can be empty if the user has no quizzes.
     */
    public void execute(String username, List<String> quizzes) {
        final DashboardInputData dashboardInputData = new DashboardInputData(username, quizzes);
        dashboardInteractor.execute(dashboardInputData);
    }
    /**
     * Executes the dashboard use case with the given username and quizzes.
     */

    public void switchToCreateQuizView() {
        dashboardInteractor.switchToCreateQuizView();
    }
    /**
     * Executes the dashboard use case with the given username and quizzes.
     * @param username username
     * @param quizName quiz name
     */

    public void switchToViewQuizView(String username, String quizName) {
        System.out.println("DashboardController: Viewing quiz " + quizName);
        dashboardInteractor.switchToViewQuizView(username, quizName);
    }

}
