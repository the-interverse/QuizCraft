package use_case.create_quiz;
import entity.Quiz;

/**
 * Output Data for the Login Use Case.
 */
public class CreateQuizOutputData {

    private Quiz quiz;

    public CreateQuizOutputData(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

}
