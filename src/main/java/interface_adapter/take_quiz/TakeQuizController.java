package interface_adapter.take_quiz;

import use_case.take_quiz.TakeQuizInputBoundary;
import use_case.take_quiz.TakeQuizInputData;
import use_case.take_quiz.TakeQuizInteractor;

import java.util.Map;


/**
 * The controller for Take Quiz use case
 */
public class TakeQuizController {

    private final TakeQuizInputBoundary takeQuizInteractor;

    public TakeQuizController(TakeQuizInputBoundary takeQuizInputBoundary) {
        this.takeQuizInteractor = takeQuizInputBoundary;
    }


    /**
     * Executes the Take Quiz use case
     * @param givenAnswers given answers to questions by user
     * @param username username of the use who took the quiz
     * @param quizName name of the quiz user took
     */
    public void execute(Map<Integer, Character> givenAnswers, String username, String quizName){
        final TakeQuizInputData takeQuizInputData = new TakeQuizInputData(givenAnswers, username, quizName);
        takeQuizInteractor.execute(takeQuizInputData);
    }
}
