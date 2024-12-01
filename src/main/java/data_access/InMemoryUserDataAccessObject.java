
package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Quiz;
import entity.QuizQuestion;
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
        boolean exists = false;
        final List<Quiz> userQuizzes = quizzes.get(username);
        if (userQuizzes == null) {
            exists = false;
        }
        else {
            for (Quiz quiz : userQuizzes) {
                if (quiz.getName().equals(quizName)) {
                    exists = true;
                }
            }
        }
        return exists;
    }

    @Override
    public void saveQuiz(Quiz quiz, String username) {
        if (quizzes.containsKey(username)) {
            quizzes.get(username).add(quiz);
        }
        else {
            final List<Quiz> userQuizzes = new ArrayList<>();
            userQuizzes.add(quiz);
            quizzes.put(username, userQuizzes);
        }
    }

    @Override
    public List<String> getQuizzes(String username) {
        if (quizzes.containsKey(username)) {
            final List<String> quizNames = new ArrayList<>();
            for (Quiz userQuiz : quizzes.get(username)) {
                quizNames.add(userQuiz.getName());
            }
        }
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getQuizData(String username, String quizName) {
        if (quizzes.containsKey(username)) {
            for (Quiz userQuiz: quizzes.get(username)) {
                if (userQuiz.getName().equals(quizName)) {
                    final List<Map<String, Object>> quizData = new ArrayList<>();
                    for (QuizQuestion quizQuestion : userQuiz.getQuestions()) {
                        quizData.add(Map.of("question", quizQuestion.getQuestion()));
                        quizData.add(Map.of("correctAnswer", quizQuestion.getCorrectIndex()));
                        final List<String> answers = new ArrayList<>();
                        answers.addAll(quizQuestion.getAnswers());
                        quizData.add(Map.of("answers", answers));
                    }
                }
            }
        }
        return List.of();
    }
}
