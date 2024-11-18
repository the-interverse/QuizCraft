package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import entity.Quiz;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.util.List;

/**
 * The Presenter for the Dashboard Use Case.
 */
public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        List<Quiz> quizzes = outputData.getQuizzes();
        DashboardState state = dashboardViewModel.getState();
        state.setQuizzes(quizzes);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        DashboardState state = dashboardViewModel.getState();
        state.setErrorMessage(errorMessage);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }
    @Override
    public void switchToCreateQuizView() {
        viewManagerModel.setState(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}

