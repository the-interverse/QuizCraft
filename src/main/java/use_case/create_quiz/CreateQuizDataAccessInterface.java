package use_case.create_quiz;

import entity.Quiz;

/**
 * DAO for the Login Use Case.
 */
public interface CreateQuizDataAccessInterface {

    /**
     * Checks if the given username has a quiz with the given name.
     * @param username the username to look for
     * @param quizName the quiz name to look for
     * @return true if a user with the given username has a quiz with a given name; otherwise, false.
     */
    boolean quizExistsByName(String username, String quizName);

    /**
     * Saves the quiz.
     * @param quiz the quiz to save in data access
     */
    void saveQuiz(Quiz quiz);
}
