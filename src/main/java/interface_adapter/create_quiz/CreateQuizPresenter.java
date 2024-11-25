package interface_adapter.create_quiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.view_quiz.ViewQuizViewModel;
import use_case.create_quiz.CreateQuizOutputBoundary;
import use_case.create_quiz.CreateQuizOutputData;

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
//        On success, switch to ViewQuiz view
//        CreateQuizState state = createQuizViewModel.getState();
//        state.setQuizName(data.getQuizName());
//        state.setDifficulty(data.getQuiz().getDifficulty());

        System.out.println("Quiz Created Successfully: " + data.getQuizName());
//        createQuizViewModel.firePropertyChanged();
//
//        viewManagerModel.setState(loggedInViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        CreateQuizState state = createQuizViewModel.getState();
        state.setErrorMessage(errorMessage);
        System.out.println("Failed to create quiz: " + errorMessage);
        createQuizViewModel.firePropertyChanged();
    }

    @Override
    public void switchToDashboardView() {
        viewManagerModel.setState(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
