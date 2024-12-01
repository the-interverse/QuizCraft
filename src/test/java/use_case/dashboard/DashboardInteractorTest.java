package use_case.dashboard;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DashboardInteractorTest {

    @Test
    void executeSuccessTest() {
        DashboardInputData inputData = new DashboardInputData("Egor", List.of());
        DashboardDataAccessInterface mockDataAccess = new DashboardDataAccessInterface() {
            @Override
            public List<String> getQuizzes(String username) {
                assertEquals("Egor", username);
                return List.of("Quiz1", "Quiz2");
            }

            @Override
            public List<Map<String, Object>> getQuizData(String username, String quizName) {
                fail("getQuizData should not be called during execute()");
                return null;
            }
        };

        DashboardOutputBoundary mockPresenter = new DashboardOutputBoundary() {
            @Override
            public void prepareSuccessView(DashboardOutputData outputData) {
                assertEquals(2, outputData.getQuizzes().size());
                assertTrue(outputData.getQuizzes().contains("Quiz1"));
                assertTrue(outputData.getQuizzes().contains("Quiz2"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure is not expected in this test.");
            }

            @Override
            public void switchToCreateQuizView() {
                fail("Switching views is not expected in this test.");
            }

            @Override
            public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
                fail("Switching views is not expected in this test.");
            }
        };

        DashboardInputBoundary interactor = new DashboardInteractor(mockDataAccess, mockPresenter);
        interactor.execute(inputData);
    }

    @Test
    void executeNoQuizzesFoundTest() {
        DashboardInputData inputData = new DashboardInputData("Egor", List.of());

        DashboardDataAccessInterface mockDataAccess = new DashboardDataAccessInterface() {
            @Override
            public List<String> getQuizzes(String username) {
                assertEquals("Egor", username);
                return List.of();
            }

            @Override
            public List<Map<String, Object>> getQuizData(String username, String quizName) {
                fail("getQuizData should not be called.");
                return null;
            }
        };

        DashboardOutputBoundary mockPresenter = new DashboardOutputBoundary() {
            @Override
            public void prepareSuccessView(DashboardOutputData outputData) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No quizzes found.", errorMessage);
            }

            @Override
            public void switchToCreateQuizView() {
                fail("Switching to Create Quiz View is not expected.");
            }

            @Override
            public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
                fail("Switching to View Quiz View is not expected.");
            }
        };

        DashboardInputBoundary interactor = new DashboardInteractor(mockDataAccess, mockPresenter);

        interactor.execute(inputData);
    }


    @Test
    void switchToCreateQuizViewTest() {
        DashboardOutputBoundary mockPresenter = new DashboardOutputBoundary() {
            @Override
            public void prepareSuccessView(DashboardOutputData outputData) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure is not expected.");
            }

            @Override
            public void switchToCreateQuizView() {
                assertTrue(true, "Switch to Create Quiz View triggered.");
            }

            @Override
            public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
                fail("Switching to View Quiz is not expected.");
            }
        };

        DashboardInputBoundary interactor = new DashboardInteractor(null, mockPresenter);
        interactor.switchToCreateQuizView();
    }

    @Test
    void switchToViewQuizViewTest() {
        DashboardDataAccessInterface mockDataAccess = new DashboardDataAccessInterface() {
            @Override
            public List<String> getQuizzes(String username) {
                fail("getQuizzes should not be called during.");
                return null;
            }

            @Override
            public List<Map<String, Object>> getQuizData(String username, String quizName) {
                assertEquals("Egor", username);
                assertEquals("Quiz1", quizName);
                return List.of(
                        Map.of("question", "What is Java?", "options", List.of("Language", "Animal"), "answer", "Language"),
                        Map.of("question", "What is Python?", "options", List.of("Language", "Snake"), "answer", "Language")
                );
            }
        };

        DashboardOutputBoundary mockPresenter = new DashboardOutputBoundary() {
            @Override
            public void prepareSuccessView(DashboardOutputData outputData) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure is not expected.");
            }

            @Override
            public void switchToCreateQuizView() {
                fail("Switching to Create Quiz.");
            }

            @Override
            public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
                assertEquals(2, quizData.size());
                assertEquals("Quiz1", quizName);
                assertEquals("What is Java?", quizData.get(0).get("question"));
                assertEquals("Language", quizData.get(0).get("answer"));
                assertEquals("What is Python?", quizData.get(1).get("question"));
            }
        };

        DashboardInputBoundary interactor = new DashboardInteractor(mockDataAccess, mockPresenter);
        interactor.switchToViewQuizView("Egor", "Quiz1");
    }

    @Test
    void invalidUsernameTest() {
        DashboardInputData inputData = new DashboardInputData(null, List.of());
        DashboardDataAccessInterface mockDataAccess = new DashboardDataAccessInterface() {
            @Override
            public List<String> getQuizzes(String username) {
                fail("Data access should not be called with an invalid username.");
                return null;
            }

            @Override
            public List<Map<String, Object>> getQuizData(String username, String quizName) {
                fail("Data access should not be called with an invalid username.");
                return null;
            }
        };

        DashboardOutputBoundary mockPresenter = new DashboardOutputBoundary() {
            @Override
            public void prepareSuccessView(DashboardOutputData outputData) {
                fail("Success is not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Invalid username.", errorMessage);
            }

            @Override
            public void switchToCreateQuizView() {
                fail("Switching to Create Quiz is not expected.");
            }

            @Override
            public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
                fail("Switching to View Quiz is not expected.");
            }
        };

        DashboardInputBoundary interactor = new DashboardInteractor(mockDataAccess, mockPresenter);
        interactor.execute(inputData);
    }

        @Test
        void testValidInputData() {
            List<String> quizzes = List.of("Quiz1", "Quiz2");
            DashboardInputData inputData = new DashboardInputData("Egor", quizzes);

            assertEquals("Egor", inputData.getUsername());
            assertEquals(2, inputData.getQuizzes().size());
            assertTrue(inputData.getQuizzes().contains("Quiz1"));
            assertTrue(inputData.getQuizzes().contains("Quiz2"));
        }

        @Test
        void testEmptyQuizzes() {
            List<String> quizzes = List.of();
            DashboardInputData inputData = new DashboardInputData("Egor", quizzes);

            assertEquals("Egor", inputData.getUsername());
            assertTrue(inputData.getQuizzes().isEmpty());
        }

        @Test
        void testNullQuizzes() {
            DashboardInputData inputData = new DashboardInputData("Egor", null);

            assertEquals("Egor", inputData.getUsername());
            assertNull(inputData.getQuizzes());
        }

        @Test
        void testNullUsername() {
            List<String> quizzes = List.of("Quiz1");
            DashboardInputData inputData = new DashboardInputData(null, quizzes);

            assertNull(inputData.getUsername());
            assertEquals(1, inputData.getQuizzes().size());
            assertTrue(inputData.getQuizzes().contains("Quiz1"));
        }

}





