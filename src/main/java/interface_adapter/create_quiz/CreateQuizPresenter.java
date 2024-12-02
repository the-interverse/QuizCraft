package interface_adapter.create_quiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.view_and_take_quiz.ViewQuizViewModel;
import java.util.Map;
import use_case.create_quiz.CreateQuizOutputBoundary;
import use_case.create_quiz.CreateQuizOutputData;

import java.util.List;

public class CreateQuizPresenter implements CreateQuizOutputBoundary {

    private ViewQuizViewModel viewQuizViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateQuizViewModel createQuizViewModel;
    private DashboardViewModel dashboardViewModel;

    public CreateQuizPresenter(ViewManagerModel viewManagerModel,
                               ViewQuizViewModel viewQuizViewModel,
                           CreateQuizViewModel createQuizViewModel,
                               DashboardViewModel dashboardViewModel) {
        this.viewQuizViewModel = viewQuizViewModel;
        this.viewManagerModel = viewManagerModel;
        this.createQuizViewModel = createQuizViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(CreateQuizOutputData data) {
        viewManagerModel.setState(viewQuizViewModel.getViewName());
        viewQuizViewModel.getState().setQuizQuestionsAndOptions(data.getQuestions());
        viewQuizViewModel.getState().setQuizName(data.getQuizName());
        viewQuizViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CreateQuizState state = createQuizViewModel.getState();
        state.setErrorMessage(errorMessage);
        createQuizViewModel.setState(state);
        createQuizViewModel.firePropertyChanged();
    }

    @Override
    public void switchToDashboardView(List<String> quizzes) {
        final DashboardState state = dashboardViewModel.getState();
        state.setQuizzes(quizzes);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
        viewManagerModel.setState(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
