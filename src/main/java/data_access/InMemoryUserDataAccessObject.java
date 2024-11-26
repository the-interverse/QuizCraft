package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Quiz;
import entity.User;
import use_case.create_quiz.CreateQuizDataAccessInterface;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        CreateQuizDataAccessInterface,
        DashboardDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, List<Quiz>> quizzes = new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public boolean quizExistsByName(String username, String quizName) {
        List<Quiz> userQuizzes = quizzes.get(username);
        if (userQuizzes == null) {
            return false;
        }
//        else {
//
        return false;
    }

    @Override
    public void saveQuiz(Quiz quiz, String username) {

    }

    @Override
    public List<String> getQuizzes(String username) {
        return List.of();
    }


    //TODO: This must also be implemented
    @Override
    public List<Map<String, Object>> getQuizData(String username, String quizName) {
        return null;
    }

}
