package use_case.create_quiz;


import entity.User;
/**
 * DAO for the Login Use Case.
 */
public interface CreateQuizDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param userUsername the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByUser(String userUsername);

    /**
     * Saves the user.
     * @param userUsername the user to save
     * @param quiz quiz to save
     */
    void saveQuiz(User userUsername, Quiz quiz);

}
