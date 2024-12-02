package entity;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Factory for creating quizzes.
 */
public class QuizFactory {

    private static final Gson GSON = new Gson();

    /**
     * Factory for creating a quiz.
     * @param json json format of the quiz
     * @param difficulty difficulty of the quiz
     * @return quiz object
     */
    public Quiz create(String json, String difficulty) {
        try {
            final Quiz quiz = GSON.fromJson(json, Quiz.class);
            return new Quiz(quiz.getName(), quiz.getQuestions(), difficulty);
        }
        catch (JsonSyntaxException exception) {
            return null;
        }
    }

    /**
     * Overloaded method for creating a quiz with provided parameters.
     * @param quizName quiz name
     * @param questions questions
     * @param difficulty difficulty
     * @return quiz
     */
    public Quiz create(String quizName, List<QuizQuestion> questions, String difficulty) {
        return new Quiz(quizName, questions, difficulty);
    }
}
