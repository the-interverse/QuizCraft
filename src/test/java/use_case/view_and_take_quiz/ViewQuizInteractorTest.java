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
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
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

        ViewQuizInputBoundary interactor = new ViewQuizInteractor(presenter);
        interactor.execute(inputData);
    }

    @Test
    void executeQuizNameNullTest() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                assertEquals("Quiz name cannot be empty.", message);
            }

            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                fail("This case should not succeed.");
            }

            @Override
            public void switchToDashboardView() {}
        };
        ViewQuizInteractor interactor = new ViewQuizInteractor(presenter);

        List<Map<String, Object>> questionsAndOptions = new ArrayList<>();
        Map<String, Object> question1 = new HashMap<>();
        question1.put("question", "Sample Question");
        question1.put("answers", Arrays.asList("Option A", "Option B", "Option C", "Option D"));
        question1.put("correctAnswer", 0);
        questionsAndOptions.add(question1);

        ViewQuizInputData inputData = new ViewQuizInputData(questionsAndOptions, "Yasser", null);

        interactor.execute(inputData);
    }

    @Test
    void executeQuizNameEmpty() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                assertEquals("Quiz name cannot be empty.", message);
            }

            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                fail("This case should not succeed.");
            }

            @Override
            public void switchToDashboardView() {}
        };
        ViewQuizInteractor interactor = new ViewQuizInteractor(presenter);

        List<Map<String, Object>> questionsAndOptions = new ArrayList<>();
        Map<String, Object> question1 = new HashMap<>();
        question1.put("question", "Sample Question");
        question1.put("answers", Arrays.asList("Option A", "Option B", "Option C", "Option D"));
        question1.put("correctAnswer", 0);
        questionsAndOptions.add(question1);

        ViewQuizInputData inputData = new ViewQuizInputData(questionsAndOptions, "Yasser", "");

        interactor.execute(inputData);
    }


    @Test
    void executeQuestionsAndOptionsNull() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                assertEquals("Quiz questions cannot be empty.", message);
            }

            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                fail("This case should not succeed.");
            }

            @Override
            public void switchToDashboardView() {}
        };
        ViewQuizInteractor interactor = new ViewQuizInteractor(presenter);

        ViewQuizInputData inputData = new ViewQuizInputData(null, "Yasser", "Git Quiz");

        interactor.execute(inputData);
    }

    @Test
    void executeQuestionsAndOptionsEmpty() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                assertEquals("Quiz questions cannot be empty.", message);
            }

            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                fail("This case should not succeed.");
            }

            @Override
            public void switchToDashboardView() {}
        };
        ViewQuizInteractor interactor = new ViewQuizInteractor(presenter);

        ViewQuizInputData inputData = new ViewQuizInputData(new ArrayList<>(), "Yasser", "Git Quiz");

        interactor.execute(inputData);
    }

    @Test
    void executeValidInputTest() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                fail("This case should not fail.");
            }

            @Override
            public void prepareSuccessView(ViewQuizOutputData outputData) {
                assertEquals("Git Quiz", outputData.getQuizName());
                assertEquals(2, outputData.getQuestionsAndOptions().size());
            }

            @Override
            public void switchToDashboardView() {}
        };
        ViewQuizInteractor interactor = new ViewQuizInteractor(presenter);

        List<Map<String, Object>> questionsAndOptions = new ArrayList<>();
        Map<String, Object> question1 = new HashMap<>();
        question1.put("question", "What is the purpose of version control?");
        question1.put("answers", Arrays.asList("Option A", "Option B", "Option C", "Option D"));
        question1.put("correctAnswer", 0);

        Map<String, Object> question2 = new HashMap<>();
        question2.put("question", "Which command is used to add changes?");
        question2.put("answers", Arrays.asList("Option A", "Option B", "Option C", "Option D"));
        question2.put("correctAnswer", 0);

        questionsAndOptions.add(question1);
        questionsAndOptions.add(question2);

        ViewQuizInputData inputData = new ViewQuizInputData(questionsAndOptions, "Yasser", "Git Quiz");

        interactor.execute(inputData);
    }

    @Test
    void getUsernameTest() {
        List<Map<String, Object>> questionsAndOptions = new ArrayList<>();
        Map<String, Object> question = new HashMap<>();
        question.put("question", "Sample Question");
        question.put("answers", Arrays.asList("Option A", "Option B", "Option C", "Option D"));
        question.put("correctAnswer", 0);
        questionsAndOptions.add(question);

        ViewQuizInputData inputData = new ViewQuizInputData(questionsAndOptions, "Yasser", "Git Quiz");

        assertEquals("Yasser", inputData.getUsername());
    }


    @Test
    void switchToDashboardViewTest() {
        ViewQuizOutputBoundary presenter = new ViewQuizOutputBoundary() {
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

        ViewQuizInputBoundary interactor = new ViewQuizInteractor(presenter);
        interactor.switchToDashboardView();
    }
}





