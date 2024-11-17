package interface_adapter.create_quiz;

import use_case.create_quiz.parsers.TextExtractor;
import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInputData;

import java.io.IOException;

public class CreateQuizController {
    private final CreateQuizInputBoundary interactor;
    private TextExtractor textExtractor;

    public CreateQuizController(CreateQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void handleCreateQuiz(String quizName, int numQuestions, String difficulty, String filePath) {
        try {
            String courseMaterial = textExtractor.extractText(filePath);
            CreateQuizInputData inputData = new CreateQuizInputData(quizName, numQuestions, difficulty, courseMaterial);
            interactor.execute(inputData);
        } catch (IOException e) {
            System.out.println("Parsing error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong file format: " + e.getMessage());
        }
    }

    public void switchToDashboardView() {
        interactor.switchToDashboardView();
    }
}
