package interface_adapter.dashboard;
import interface_adapter.ViewManagerModel;
import use_case.dashboard.LoggedInOutputBoundary;
import use_case.dashboard.LoggedInOutputData;

public class LoggedInPresenter implements LoggedInOutputBoundary {

    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
                             LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }


    @Override
    public void prepareSuccessView(LoggedInOutputData data) {
        System.out.println("Dashboard loaded.");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Failed to load dashboard: " + errorMessage);
    }

    @Override
    public void switchToCreateQuizView() {
        viewManagerModel.setState("CreateQuizView");
        viewManagerModel.firePropertyChanged();
    }
}
