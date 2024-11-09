package use_case.create_quiz;
import entity.Quiz;

/**
 * Output Data for the CreateQuiz Use Case.
 */
public class CreateQuizOutputData {

    private final Quiz quiz;

    public CreateQuizOutputData(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

}
