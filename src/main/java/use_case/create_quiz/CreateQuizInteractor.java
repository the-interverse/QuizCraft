package use_case.create_quiz;
import entity.Quiz;
import file_parser.PDFParser;

import java.util.ArrayList;
/**
 * The Login Interactor.
 */
public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private final CreateQuizOutputBoundary createQuizPresenter;
    // private final PDFParser pdfParser;

    public CreateQuizInteractor(CreateQuizOutputBoundary createQuizPresenter) {
       // this.pdfParser = pdfParser;
        this.createQuizPresenter = createQuizPresenter;
    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        if (createQuizInputData.getQuizName() == null || createQuizInputData.getQuizName().isEmpty()) {
            createQuizPresenter.prepareFailView("Quiz name cannot be empty.");
            return;
        }

        Quiz quiz = new Quiz(createQuizInputData.getQuizName(), new ArrayList<>(), createQuizInputData.getDifficulty());
        createQuizPresenter.prepareSuccessView(new CreateQuizOutputData(quiz));
    }
}
