package use_case.take_quiz;

/**
 * The Login Interactor.
 */
public class TakeQuizInteractor implements LoginInputBoundary {
    private final TakeQuizDataAccessInterface userDataAccessObject;
    private final TakeQuizOutputBoundary loginPresenter;

    public TakeQuizInteractor(TakeQuizDataAccessInterface userDataAccessInterface,
                              TakeQuizOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(TakeQuizInputData takeQuizInputData) {
        final String username = takeQuizInputData.getUsername();
        final String password = takeQuizInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(takeQuizInputData.getUsername());

                userDataAccessObject.setCurrentUsername(user.getName());
                final TakeQuizOutputData loginOutputData = new TakeQuizOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
