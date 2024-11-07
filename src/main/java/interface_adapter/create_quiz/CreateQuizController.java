package interface_adapter.create_quiz;

import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInputData;

import java.io.File;

public class CreateQuizController {
    private final CreateQuizInputBoundary interactor;

    public CreateQuizController(CreateQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void handleCreateQuiz(String quizName, int numQuestions, String difficulty, File pdf) {
        CreateQuizInputData inputData = new CreateQuizInputData(quizName, numQuestions, difficulty, pdf);
        interactor.execute(inputData);
    }

    public void switchToDashboardView() {
        interactor.switchToDashboardView();
    }
}
