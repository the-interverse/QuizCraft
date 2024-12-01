package use_case.create_quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import ai_access.AIAccessInterface;
import entity.Quiz;
import entity.QuizFactory;
import entity.QuizQuestion;
import parsers.TextExtractor;

/**
 * The Login Interactor.
 */
public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private static final Integer QUESTION_LIMIT = 5;
    private static final Integer QUESTION_MIN = 1;
    private final CreateQuizDataAccessInterface quizDataAccessObject;
    private final CreateQuizOutputBoundary createQuizPresenter;
    private final QuizFactory quizFactory;
    private final AIAccessInterface cohereApi;

    public CreateQuizInteractor(CreateQuizOutputBoundary createQuizPresenter,
                                CreateQuizDataAccessInterface quizDataAccessObject,
                                QuizFactory quizFactory,
                                AIAccessInterface aiAccesObject) {
        this.createQuizPresenter = createQuizPresenter;
        this.quizDataAccessObject = quizDataAccessObject;
        this.quizFactory = quizFactory;
        this.cohereApi = aiAccesObject;
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
        }
        else if (numQuestions < QUESTION_MIN) {
            createQuizPresenter
                .prepareFailView("Number of questions cannot be less than 1. Please choose more questions");
        }
        else if (numQuestions > QUESTION_LIMIT) {
            createQuizPresenter
                .prepareFailView("Number of questions cannot be more than 5. Please choose less questions");
        }
        else if (quizDataAccessObject.quizExistsByName(username, quizName)) {
            createQuizPresenter.prepareFailView("Quiz with this name already exists. Please choose another name");
        }
        else {
            try {
                final String courseMaterial = TextExtractor.extractText(filePath);
                String quizJSON = "";
                try {
                    quizJSON = cohereApi.callAPI(courseMaterial, quizName, numQuestions, difficulty);
                    final Quiz quiz = quizFactory.create(quizJSON, difficulty);
                    if (quiz == null) {
                        createQuizPresenter.prepareFailView("Quiz could not be created. Please try again.");
                    }
                    else {
                        quizDataAccessObject.saveQuiz(quiz, username);
                        final CreateQuizOutputData createQuizOutputData = new CreateQuizOutputData(quizName,
                            getQuestions(quiz));
                        createQuizPresenter.prepareSuccessView(createQuizOutputData);
                    }
                }
                catch (IOException exception) {
                    createQuizPresenter.prepareFailView(exception.getMessage());
                }
            }
            catch (IOException exception) {
                createQuizPresenter.prepareFailView("Parsing error: " + exception.getMessage());
            }
            catch (IllegalArgumentException exception) {
                createQuizPresenter.prepareFailView("Wrong file format: " + exception.getMessage());
            }
        }
    }

    @NotNull
    private static List<Map<String, Object>> getQuestions(Quiz quiz) {
        final List<Map<String, Object>> questions = new ArrayList<>();
        for (QuizQuestion question : quiz.getQuestions()) {
            final Map questionInfo = new HashMap<>();
            questionInfo.put("question", question.getQuestion());
            questionInfo.put("correctAnswer", question.getCorrectIndex());
            final List<String> answers = new ArrayList<>();
            for (Integer i = 0; i < question.getAnswers().size(); i++) {
                answers.add(question.getAnswers().get(i));
            }
            questionInfo.put("answers", answers);
            questions.add(questionInfo);
        }
        return questions;
    }

    @Override
    public void switchToDashboardView(String username) {
        final List<String> quizzes = quizDataAccessObject.getQuizzes(username);
        System.out.println("test" + quizzes + "and" + username);
        createQuizPresenter.switchToDashboardView(quizzes);
    }
}
