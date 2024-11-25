package use_case.createquiz;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import entity.UserFactory;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import use_case.create_quiz.CreateQuizInputData;
import use_case.create_quiz.CreateQuizOutputBoundary;
import use_case.create_quiz.CreateQuizOutputData;
import use_case.login.*;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CreateQuizInteractorTest {

    @Before
    public void setUp() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        UserFactory factory = new UserFactory();
        User user = factory.create("kirill", "123456");
        userRepository.save(user);
        userRepository.setCurrentUsername("kirill");
    }

    @Test
    void successTest() {
        CreateQuizInputData inputData = new CreateQuizInputData("Git Quiz", 3, "Easy", "src/test/resources/02-Git.pdf", "kirill");
        // This creates a successPresenter that tests whether the test case is as we expect.
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {

            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToDashboardView() {

            }
        };

//        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
//        interactor.execute(inputData);
    }

    @Test
    void successCreateQuizTest() {
//        TODO
    }

    @Test
    void failureCreateQuizTest() {
//        TODO
    }
}
