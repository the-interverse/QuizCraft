package interface_adapter.create_quiz;

import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInputData;

import java.io.IOException;
import java.util.List;

public class CreateQuizController {
    private final CreateQuizInputBoundary createQuizInteractor;

    public CreateQuizController(CreateQuizInputBoundary interactor) {
        this.createQuizInteractor = interactor;
    }

    public void execute(String quizName, int numQuestions, String difficulty, String filePath, String username) {
        CreateQuizInputData inputData = new CreateQuizInputData(quizName, numQuestions, difficulty, filePath, username);
        createQuizInteractor.execute(inputData);
    }

    public void switchToDashboardView(String username) {
        createQuizInteractor.switchToDashboardView(username);
    }
}
