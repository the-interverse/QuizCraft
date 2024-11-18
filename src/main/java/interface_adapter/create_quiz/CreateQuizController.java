package interface_adapter.create_quiz;

import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInputData;

import java.io.IOException;

public class CreateQuizController {
    private final CreateQuizInputBoundary createQuizInteractor;

    public CreateQuizController(CreateQuizInputBoundary interactor) {
        this.createQuizInteractor = interactor;
    }

    public void execute(String quizName, int numQuestions, String difficulty, String filePath) {
        CreateQuizInputData inputData = new CreateQuizInputData(quizName, numQuestions, difficulty, filePath);
        createQuizInteractor.execute(inputData);
    }

    public void switchToDashboardView() {
        createQuizInteractor.switchToDashboardView();
    }
}
