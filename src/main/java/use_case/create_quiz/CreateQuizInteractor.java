package use_case.create_quiz;
import entity.Quiz;
import use_case.create_quiz.parsers.TextExtractor;
import use_case.create_quiz.cohere_interaction.CohereAPI;
import java.io.IOException;
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
        }
        if (numQuestions < 1) {
            createQuizPresenter.prepareFailView("Number of questions cannot be less than 1.");
        }
        try {
            String courseMaterial = TextExtractor.extractText(filePath);
            final CohereAPI cohereAPI = new CohereAPI();
            String prompt = cohereAPI.createPrompt(courseMaterial, quizName, numQuestions, difficulty);
            String quizJSON = "";
            try {
                quizJSON = cohereAPI.callAPI(prompt);
            } catch (Exception e) {
                createQuizPresenter.prepareFailView(e.getMessage());
            }
            Quiz quiz = CohereAPI.parseQuiz(quizJSON, difficulty);
        } catch (IOException e) {
            createQuizPresenter.prepareFailView("Parsing error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            createQuizPresenter.prepareFailView("Wrong file format: " + e.getMessage());
        }
    }

    @Override
    public void switchToDashboardView() {
        createQuizPresenter.switchToDashboardView();
    }
}
