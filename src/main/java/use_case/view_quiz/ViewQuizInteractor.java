package use_case.view_quiz;

/**
 * The Login Interactor.
 */
public class ViewQuizInteractor implements ViewQuizInputBoundary {
    private final ViewQuizDataAccessInterface userDataAccessObject;
    private final ViewQuizOutputBoundary loginPresenter;

    public ViewQuizInteractor(ViewQuizDataAccessInterface userDataAccessInterface,
                              ViewQuizOutputBoundary viewQuizOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = viewQuizOutputBoundary;
    }

    @Override
    public void execute(ViewQuizInputData loginInputData) {
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
                final ViewQuizOutputData loginOutputData = new ViewQuizOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
