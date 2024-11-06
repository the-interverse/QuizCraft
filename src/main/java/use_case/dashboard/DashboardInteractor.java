package use_case.dashboard;

/**
 * The Login Interactor.
 */
public class DashboardInteractor implements DashboardInputBoundary {
    private final DashboardDataAccessInterface userDataAccessObject;
    private final DashboardOutputBoundary loginPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessInterface,
                               DashboardOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(DashboardInputData loginInputData) {
        }
}