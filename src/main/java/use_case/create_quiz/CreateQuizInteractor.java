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
        final String quizName = createQuizInputData.getQuizName();
        final Integer numQuestions = createQuizInputData.getNumQuestions();
        final String difficulty = createQuizInputData.getDifficulty();
        final String filePath = createQuizInputData.getFilepath();
        if (quizName == null || quizName.isEmpty()) {
            createQuizPresenter.prepareFailView("Quiz name cannot be empty.");
        if (numQuestions < 1) {
            createQuizPresenter.prepareFailView("Number of questions cannot be less than 1.");
        }

        }

        Quiz quiz = new Quiz(createQuizInputData.getQuizName(), new ArrayList<>(), createQuizInputData.getDifficulty());
        createQuizPresenter.prepareSuccessView(new CreateQuizOutputData(quiz));
    }

    @Override
    public void switchToDashboardView() {
        createQuizPresenter.switchToDashboardView();
    }
}
