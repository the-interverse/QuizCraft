package interface_adapter.dashboard;

import entity.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Dashboard View.
 */
public class DashboardState {
    private final List<Quiz> quizzes = new ArrayList<>();
    private String errorMessage;
    private String username;

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes.clear();
        this.quizzes.addAll(quizzes);
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public static class QuizDisplayData {
        private final String quizName;
        private final String score;

        public QuizDisplayData() {
            this.quizName = "";
            this.score = "";
        }
        public QuizDisplayData(String quizName, String score) {
            this.quizName = quizName;
            this.score = score;
        }

        public String getQuizName() {
            return quizName;
        }

        public String getScore() {
            return score;
        }
    }
}
