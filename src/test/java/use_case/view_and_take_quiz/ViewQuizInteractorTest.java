package use_case.view_and_take_quiz;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewQuizInteractorTest {

    @Test
    void executeSuccessTest() {
        List<Map<String, Object>> questionsAndOptions = new ArrayList<>();
        Map<String, Object> question1 = new HashMap<>();
        Map<String, Object> question2 = new HashMap<>();
        Map<String, Object> question3 = new HashMap<>();
        question1.put("question", "What is the purpose of version control in software development?");
        question1.put("answers", Arrays.asList("Version control is used to manage and track changes made to software code over time, allowing developers to collaborate effectively and maintain a history of modifications", "It is a way to create multiple versions of a software project, with each version having its own unique features and functionality", "Version control is a method to backup and restore software code in case of accidental deletions or modifications", "It is a tool for managing different branches of a software project, enabling developers to work on multiple features simultaneously"));
        question1.put("correctAnswer", 0);

        question2.put("question", "Which command is used to add changes to the next commit in Git?");
        question2.put("answers", Arrays.asList("git add", "git commit", "git push", "git pull"));
        question2.put("correctAnswer", 0);

        question3.put("question", "What does the 'git status' command do?");
        question3.put("answers", Arrays.asList("It displays the current branch and the latest commit message", "It shows the differences between the working directory and the staged files", "It provides information about the status of files in the repository, indicating if they are untracked, tracked, staged, or modified", "It lists all the files in the repository and their corresponding commit history"));
        question3.put("correctAnswer", 2);

        questionsAndOptions.add(question1);
        questionsAndOptions.add(question2);
        questionsAndOptions.add(question3);

        ViewQuizInputData inputData = new ViewQuizInputData(questionsAndOptions, "Yasser", "Git Quiz");
        ViewQuizDataAccessInterface mockDataAccess = new ViewQuizDataAccessInterface() {};

        ViewQuizOutputBoundary mockPresenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                assertEquals(3, outputData.getQuestionsAndOptions().size());
                assertTrue(outputData.getQuestionsAndOptions().get(0).containsKey("question"));
                assertTrue(outputData.getQuestionsAndOptions().get(0).containsKey("answers"));
                assertTrue(outputData.getQuestionsAndOptions().get(0).containsKey("correctAnswer"));

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure is not expected in this test.");
            }

            @Override
            public void switchToDashboardView() {
                fail("Switching views is not expected in this test.");
            }
        };

        ViewQuizInputBoundary interactor = new ViewQuizInteractor(mockPresenter);
        interactor.execute(inputData);
    }

    @Test
    void switchToDashboardViewTest() {
        ViewQuizDataAccessInterface mockDataAccess = new ViewQuizDataAccessInterface() {};

        ViewQuizOutputBoundary mockPresenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure is not expected.");
            }

            @Override
            public void switchToDashboardView() {}
        };

        ViewQuizInputBoundary interactor = new ViewQuizInteractor(mockPresenter);
        interactor.switchToDashboardView();
    }
}





