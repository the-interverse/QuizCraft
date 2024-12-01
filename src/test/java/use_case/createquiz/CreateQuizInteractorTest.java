package use_case.createquiz;

import ai_access.AIAccessInterface;
import ai_access.CohereAPI;
import data_access.InMemoryUserDataAccessObject;
import entity.Quiz;
import entity.QuizFactory;
import org.junit.jupiter.api.Test;
import use_case.create_quiz.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateQuizInteractorTest {

    @Test
    void successTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        URL resource = getClass().getResource("/createQuizTest/ACT_A_Kirill_Utyashev.pdf");
        assertNotNull(resource, "File not found in test resources");
        File file = new File(resource.getFile());
        String filePath = file.getAbsolutePath();
        CreateQuizInputData inputData = new CreateQuizInputData("ACT quiz", 1, "Easy", filePath, "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                assertEquals("ACT quiz", outputData.getQuizName());
                assertEquals(1, outputData.getQuestions().size());
                assertTrue(outputData.getQuestions().get(0).containsKey("question"));
                assertTrue(outputData.getQuestions().get(0).containsKey("correctAnswer"));
                assertTrue(outputData.getQuestions().get(0).containsKey("answers"));
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println(error);
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeNullQuizNameTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        CreateQuizInputData inputData = new CreateQuizInputData("", 1, "Easy", ".pdf", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz name cannot be empty. Please choose another name.", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeNegativeNumOfQuestionsTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", -1, "Easy", ".pdf", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Number of questions cannot be less than 1. Please choose more questions", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeMoreThanFiveQuestionsTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", 10, "Easy", ".pdf", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Number of questions cannot be more than 5. Please choose less questions", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeQuizAlreadyExistsTest() {
        CreateQuizDataAccessInterface mockDataAccess = new CreateQuizDataAccessInterface(){

            Map<String, List<String>> userQuizzes = new HashMap<>();

            @Override
            public boolean quizExistsByName(String username, String quizName) {
                if (userQuizzes.containsKey(username)) {
                    for (String quiz : userQuizzes.get(username)){
                        if (quizName.equals(quiz)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void saveQuiz(Quiz quiz, String username) {
                if (userQuizzes.containsKey(username)) {
                    userQuizzes.get(username).add(quiz.getName());
                }
                ArrayList<String> quizNames = new ArrayList<>();
                quizNames.add(quiz.getName());
                userQuizzes.put(username, quizNames);
            }

            @Override
            public List<String> getQuizzes(String username) {
                if (userQuizzes.containsKey(username)) {
                    return userQuizzes.get(username);
                }
                return List.of();
            }
        };
        String quizName = "Quiz";
        String difficulty = "Easy";
        CreateQuizInputData inputData = new CreateQuizInputData(quizName, 1, difficulty, ".pdf", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz with this name already exists. Please choose another name", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        QuizFactory quizFactory = new QuizFactory();
        mockDataAccess.saveQuiz(quizFactory.create("Quiz", List.of(), difficulty), "kirill");
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, quizFactory, new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeWrongFileFormatTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", 2, "Easy", "article.docx", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Wrong file format: Unsupported file format", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeParsingErrorTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", 2, "Easy", "article.pdf", "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue("Parsing error: article.pdf (No such file or directory)".equals(error) || "Parsing error: article.pdf (The system cannot find the file specified)".equals(error));
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), new CohereAPI());
        interactor.execute(inputData);
    }

    @Test
    void executeQuizCouldNotBeCreatedTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        URL resource = getClass().getResource("/createQuizTest/ACT_A_Kirill_Utyashev.pdf");
        assertNotNull(resource, "File not found in test resources");
        File file = new File(resource.getFile());
        String filePath = file.getAbsolutePath();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", 2, "Easy", filePath, "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz could not be created. Please try again.", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };

        AIAccessInterface mockAIAccess = new AIAccessInterface() {
            @Override
            public String callAPI(String courseMaterial, String quizName, Integer numQuestions, String difficulty) throws IOException {
                return "wrong JSON format";
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), mockAIAccess);
        interactor.execute(inputData);
    }

    @Test
    void executeCohereAPIThrowsExceptionTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        URL resource = getClass().getResource("/createQuizTest/ACT_A_Kirill_Utyashev.pdf");
        assertNotNull(resource, "File not found in test resources");
        File file = new File(resource.getFile());
        String filePath = file.getAbsolutePath();
        CreateQuizInputData inputData = new CreateQuizInputData("Quiz", 2, "Easy", filePath, "kirill");
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Test exception", error);
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                fail("Switching views is not expected in this test.");
            }
        };

        AIAccessInterface mockAIAccess = new AIAccessInterface() {
            @Override
            public String callAPI(String courseMaterial, String quizName, Integer numQuestions, String difficulty) throws IOException {
                throw new IOException("Test exception");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), mockAIAccess);
        interactor.execute(inputData);
    }

    @Test
    void switchToDashboardViewTest() {
        InMemoryUserDataAccessObject mockDataAccess = new InMemoryUserDataAccessObject();
        String username = "kirill";
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case fail is not expected.");
            }

            @Override
            public void switchToDashboardView(List<String> quizzes) {
                assertTrue(true, "Switch to Create Dashboard View triggered.");
            }
        };

        AIAccessInterface mockAIAccess = new AIAccessInterface() {
            @Override
            public String callAPI(String courseMaterial, String quizName, Integer numQuestions, String difficulty) throws IOException {
                throw new IOException("Test exception");
            }
        };
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(successPresenter, mockDataAccess, new QuizFactory(), mockAIAccess);
        interactor.switchToDashboardView(username);
    }

}