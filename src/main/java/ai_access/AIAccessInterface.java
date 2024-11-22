package ai_access;


import java.io.IOException;

/**
 * Interface for interactions with AI endpoints
 */
public interface AIAccessInterface {
    /**
     * Method that calls AI API with given parameters and returns a String that contains generated quiz
     * @param courseMaterial course material
     * @param quizName name of the quiz
     * @param numQuestions number of questions in the quiz
     * @param difficulty difficulty of the quiz
     * @return quiz info in String format
     */
    String callAPI(String courseMaterial, String quizName, Integer numQuestions, String difficulty) throws IOException;
}
