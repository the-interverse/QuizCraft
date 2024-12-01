package interface_adapter.dashboard;

import java.util.List;
import java.util.Map;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.view_and_take_quiz.ViewQuizViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

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
        final List<String> quizzes = outputData.getQuizzes();
        final DashboardState state = dashboardViewModel.getState();
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
    public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
        viewManagerModel.setState(viewQuizViewModel.getViewName());
        viewQuizViewModel.getState().setQuizQuestionsAndOptions(quizData);
        viewQuizViewModel.getState().setQuizName(quizName);
        viewQuizViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

}
