package use_case.dashboard;
import entity.User;

/**
 * The Signup Interactor.
 */
public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInOutputBoundary loggedInPresenter;

    public LoggedInInteractor(LoggedInOutputBoundary loggedInOutputBoundary) {
        this.loggedInPresenter = loggedInOutputBoundary;
    }

    @Override
    public void execute(LoggedInInputData loggedInInputData) {
    }

    @Override
    public void switchToCreateQuizView() {
        loggedInPresenter.switchToCreateQuizView();
    }
}
