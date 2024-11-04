package use_case.edit_quiz;

/**
 * The Login Interactor.
 */
public class EditQuizInteractor implements EditQuizInputBoundary {
    private final EditQuizDataAccessInterface userDataAccessObject;
    private final EditQuizOutputBoundary loginPresenter;

    public EditQuizInteractor(EditQuizDataAccessInterface userDataAccessInterface,
                              EditQuizOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(EditQuizInputData loginInputData) {
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
                final EditQuizOutputData loginOutputData = new EditQuizOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
