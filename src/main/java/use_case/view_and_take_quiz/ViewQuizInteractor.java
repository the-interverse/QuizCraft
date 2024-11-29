package use_case.view_and_take_quiz;

/**
 * The ViewQuiz Interactor.
 */
public class ViewQuizInteractor implements ViewQuizInputBoundary {
    private final ViewQuizOutputBoundary viewQuizPresenter;

    public ViewQuizInteractor(ViewQuizOutputBoundary viewQuizPresenter) {
        this.viewQuizPresenter = viewQuizPresenter;
    }

    @Override
    public void execute(ViewQuizInputData viewQuizInputData) {
        if (viewQuizInputData.getQuizName() == null || viewQuizInputData.getQuizName().isEmpty()) {
            viewQuizPresenter.prepareFailView("Quiz name cannot be empty.");
            return;
        } else if (viewQuizInputData.getQuizName() == null || viewQuizInputData.getQuizName().isEmpty()) {
            viewQuizPresenter.prepareFailView("Quiz questions cannot be empty.");
            return;
        }

        viewQuizPresenter.prepareSuccessView(new ViewQuizOutputData(viewQuizInputData.getQuizName(), viewQuizInputData.getQuestionsAndOptions()));
    }

    @Override
    public void switchToDashboardView() {
        viewQuizPresenter.switchToDashboardView();
    }
}
