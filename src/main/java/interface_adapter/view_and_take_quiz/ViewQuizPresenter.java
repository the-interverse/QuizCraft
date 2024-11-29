package interface_adapter.view_and_take_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.view_and_take_quiz.ViewQuizOutputBoundary;
import use_case.view_and_take_quiz.ViewQuizOutputData;

public class ViewQuizPresenter implements ViewQuizOutputBoundary {

    private DashboardViewModel dashboardViewModel;
    private ViewManagerModel viewManagerModel;
    private ViewQuizViewModel viewQuizViewModel;

    public ViewQuizPresenter(ViewManagerModel viewManagerModel,
                               DashboardViewModel dashboardViewModel,
                               ViewQuizViewModel viewQuizViewModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewQuizViewModel = viewQuizViewModel;
    }


    @Override
    public void prepareSuccessView(ViewQuizOutputData data) {
        ViewQuizState state = viewQuizViewModel.getState();
        state.setQuizName(data.getQuizName());
        state.setQuizQuestionsAndOptions(data.getQuestionsAndOptions());

        System.out.println("Quiz view Successful: " + data.getQuizName());
        viewQuizViewModel.firePropertyChanged();

       // viewManagerModel.setState(loggedInViewModel.getViewName());
       // viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewQuizState state = viewQuizViewModel.getState();
        state.setErrorMessage(errorMessage);
        System.out.println("Failed to view quiz: " + errorMessage);
        viewQuizViewModel.firePropertyChanged();
    }

    @Override
    public void switchToDashboardView() {
        viewManagerModel.setState(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
