package use_case.create_quiz;


import entity.Quiz;
import entity.User;
/**
 * DAO for the Login Use Case.
 */
public interface CreateQuizDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsQuizByUser(String username);

    /**
     * Saves the user.
     * @param quiz quiz to save
     */
    void saveQuiz(Quiz quiz);
}
