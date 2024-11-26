package interface_adapter.dashboard;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Dashboard View.
 */
public class DashboardState {
    private final List<String> quizNames = new ArrayList<>();
    private String errorMessage;
    private String username;

    public List<String> getQuizzes() {
        return quizNames;
    }

    public void setQuizzes(List<String> quizNames) {
        this.quizNames.clear();
        this.quizNames.addAll(quizNames);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
