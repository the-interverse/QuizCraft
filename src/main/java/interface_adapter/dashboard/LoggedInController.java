package interface_adapter.dashboard;

import use_case.dashboard.LoggedInInputBoundary;

public class LoggedInController {
    private final LoggedInInputBoundary loggedInInteractor;

    public LoggedInController(LoggedInInputBoundary loggedInInteractor) {
        this.loggedInInteractor = loggedInInteractor;
    }

    public void switchToCreateQuizView() {
        loggedInInteractor.switchToCreateQuizView();
    }
}
