package use_case.create_quiz;
import ai_access.AIAccessInterface;
import entity.Quiz;
import entity.QuizFactory;
import entity.QuizQuestion;
import org.jetbrains.annotations.NotNull;
import use_case.create_quiz.parsers.TextExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Login Interactor.
 */
public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private final CreateQuizDataAccessInterface quizDataAccessObject;
    private final CreateQuizOutputBoundary createQuizPresenter;
    private final QuizFactory quizFactory;
    private final AIAccessInterface cohereAPI;

    public CreateQuizInteractor(CreateQuizOutputBoundary createQuizPresenter,
                                CreateQuizDataAccessInterface quizDataAccessObject,
                                QuizFactory quizFactory,
                                AIAccessInterface aiAccesObject) {
        this.createQuizPresenter = createQuizPresenter;
        this.quizDataAccessObject = quizDataAccessObject;
        this.quizFactory = quizFactory;
        this.cohereAPI = aiAccesObject;
    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        final String quizName = createQuizInputData.getQuizName();
        final Integer numQuestions = createQuizInputData.getNumQuestions();
        final String difficulty = createQuizInputData.getDifficulty();
        final String filePath = createQuizInputData.getFilepath();
        final String username = createQuizInputData.getUsername();
        if (quizName == null || quizName.isEmpty()) {
            createQuizPresenter.prepareFailView("Quiz name cannot be empty. Please choose another name.");
        } else if (numQuestions < 1) {
            createQuizPresenter.prepareFailView("Number of questions cannot be less than 1. Please choose more questions");
        } else if (numQuestions > 5) {
            createQuizPresenter.prepareFailView("Number of questions cannot be more than 5. Please choose less questions");
        } else if (quizDataAccessObject.quizExistsByName(username, quizName)) {
            createQuizPresenter.prepareFailView("Quiz with this name already exists. Please choose another name");
        } else
            try {
                String courseMaterial = TextExtractor.extractText(filePath);
                String quizJSON = "";
                try {
                    quizJSON = cohereAPI.callAPI(courseMaterial, quizName, numQuestions, difficulty);
                    Quiz quiz = quizFactory.create(quizJSON, difficulty);
                    if (quiz == null) {
                        createQuizPresenter.prepareFailView("Quiz could not be created. Please try again.");
                    } else {
                        quizDataAccessObject.saveQuiz(quiz, username);
                        final List<Map<String, Object>> questions = getQuestions(quiz);
                        final CreateQuizOutputData createQuizOutputData = new CreateQuizOutputData(quizName, questions);
                        createQuizPresenter.prepareSuccessView(createQuizOutputData);
                    }
                } catch (Exception e) {
                    createQuizPresenter.prepareFailView(e.getMessage());
                }
            } catch (IOException e) {
                createQuizPresenter.prepareFailView("Parsing error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                createQuizPresenter.prepareFailView("Wrong file format: " + e.getMessage());
            }
    }

    @NotNull
    private static List<Map<String, Object>> getQuestions(Quiz quiz) {
        final List<Map<String, Object>> questions = new ArrayList<>();
        for (QuizQuestion question : quiz.getQuestions()){
            Map questionInfo = new HashMap<>();
            questionInfo.put("question", question.getQuestion());
            questionInfo.put("correctAnswer", question.getCorrectIndex());
            List<String> answers = new ArrayList<>();
            for (Integer i = 0; i < question.getAnswers().size(); i++){
                answers.add(question.getAnswers().get(i));
            }
            questionInfo.put("answers", answers);
            questions.add(questionInfo);
        }
        return questions;
    }

    @Override
    public void switchToDashboardView(String username) {
        List<String> quizzes = quizDataAccessObject.getQuizzes(username);
        System.out.println("test"+ quizzes + "and" + username);
        createQuizPresenter.switchToDashboardView(quizzes);
    }
}
