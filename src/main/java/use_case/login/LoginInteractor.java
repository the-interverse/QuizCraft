package use_case.login;

import entity.Quiz;
import entity.User;

import java.util.List;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
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
                userDataAccessObject.setCurrentUsername(user.getUsername());
                final List<String> quizzes = userDataAccessObject.getQuizzes(loginInputData.getUsername());
                final LoginOutputData loginOutputData = new LoginOutputData(user.getUsername(), false, quizzes);
                loginPresenter.prepareSuccessView(loginOutputData);

            }
        }
    }
}
