package use_case.take_quiz;
import entity.Quiz;
import entity.User;

import java.util.Map;

/**
 * DAO for take the quiz use case
 */
public interface TakeQuizDataAccessInterface {

    /**
     * Saves the result of the quiz taken by user
     * @param quiz quiz taken
     * @param user user that took the quiz
     * @param givenAnswers provided answers by user
     */
    void saveQuizResult(Quiz quiz, User user, Map<Integer, Character> givenAnswers);
}
