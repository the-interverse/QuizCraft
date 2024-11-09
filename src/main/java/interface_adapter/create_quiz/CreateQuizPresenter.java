package interface_adapter.create_quiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.LoggedInViewModel;
import use_case.create_quiz.CreateQuizOutputBoundary;
import use_case.create_quiz.CreateQuizOutputData;

public class CreateQuizPresenter implements CreateQuizOutputBoundary {

    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateQuizViewModel createQuizViewModel;

    public CreateQuizPresenter(ViewManagerModel viewManagerModel,
                           LoggedInViewModel loggedInViewModel,
                           CreateQuizViewModel createQuizViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.createQuizViewModel = createQuizViewModel;
    }


    @Override
    public void prepareSuccessView(CreateQuizOutputData data) {
        System.out.println("Quiz Created Successfully: " + data.getQuiz().getName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Failed to create quiz: " + errorMessage);
    }

    @Override
    public void switchToDashboardView() {
        viewManagerModel.setState("logged in");
        viewManagerModel.firePropertyChanged();
    }
}
