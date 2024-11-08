package use_case.create_quiz;
import entity.Quiz;
import java.util.ArrayList;
/**
 * The Login Interactor.
 */
public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private final CreateQuizOutputBoundary createQuizPresenter;

    public CreateQuizInteractor(CreateQuizOutputBoundary createQuizPresenter) {
        this.createQuizPresenter = createQuizPresenter;
    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        if (createQuizInputData.getQuizName() == null || createQuizInputData.getQuizName().isEmpty()) {
            createQuizPresenter.prepareFailView("Quiz name cannot be empty.");
            return;
        }

        Quiz quiz = new Quiz(createQuizInputData.getQuizName(), new ArrayList<>(), createQuizInputData.getDifficulty());
        createQuizPresenter.prepareSuccessView(new CreateQuizOutputData(quiz));
    }
}
