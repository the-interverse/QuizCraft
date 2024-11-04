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
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(loginInputData.getUsername());

                userDataAccessObject.setCurrentUsername(user.getName());
                final DashboardOutputData loginOutputData = new DashboardOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
