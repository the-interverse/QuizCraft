package entity;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

/**
 * Factory for creating quizzes
 */
public class QuizFactory {

    private static final Gson gson = new Gson();
    public Quiz create(String json, String difficulty){
        try {
            Quiz quiz = gson.fromJson(json, Quiz.class);
            return new Quiz(quiz.getName(), quiz.getQuestions(), difficulty); // sets the difficulty field
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public Quiz create(String quizName, List<QuizQuestion> questions, String difficulty) {
       return new Quiz(quizName, questions, difficulty);
    }
}
