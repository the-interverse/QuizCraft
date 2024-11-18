package use_case.take_quiz;

/**
 * The take quiz interactor
 */
public class TakeQuizInteractor implements TakeQuizInputBoundary{

    private final TakeQuizDataAccessInterface takeQuizDataAccessObject;
    private final TakeQuizOutputBoundary takeQuizPresenter;

    public TakeQuizInteractor(TakeQuizDataAccessInterface takeQuizDataAccessInterface,
                              TakeQuizOutputBoundary takeQuizOutputBoundary) {
        this.takeQuizDataAccessObject = takeQuizDataAccessInterface;
        this.takeQuizPresenter = takeQuizOutputBoundary;
    }

    public void execute(TakeQuizInputData takeQuizInputData){
//    TODO
    }
}
