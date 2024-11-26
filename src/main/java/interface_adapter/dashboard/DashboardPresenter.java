package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import entity.Quiz;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.view_quiz.ViewQuizViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.util.List;

/**
 * The Presenter for the Dashboard Use Case.
 */
public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel dashboardViewModel;
    private final CreateQuizViewModel createQuizViewModel;
    private final ViewQuizViewModel viewQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel,
                              CreateQuizViewModel createQuizViewModel, ViewQuizViewModel viewQuizViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.createQuizViewModel = createQuizViewModel;
        this.viewQuizViewModel = viewQuizViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        List<String> quizzes = outputData.getQuizzes();
        DashboardState state = dashboardViewModel.getState();
        state.setQuizzes(quizzes);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DashboardState state = dashboardViewModel.getState();
        state.setErrorMessage(errorMessage);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }
    @Override
    public void switchToCreateQuizView() {
        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void switchToViewQuizView() {
        viewManagerModel.setState(viewQuizViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
