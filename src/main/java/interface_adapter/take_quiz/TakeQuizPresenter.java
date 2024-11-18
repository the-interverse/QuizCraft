package interface_adapter.take_quiz;

import interface_adapter.ViewManagerModel;
import use_case.take_quiz.TakeQuizOutputBoundary;
import use_case.take_quiz.TakeQuizOutputData;
import view.ViewManager;
import view.ViewQuizView;

public class TakeQuizPresenter implements TakeQuizOutputBoundary {
    private final TakeQuizViewModel takeQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewQuizView viewQuizView;


    public TakeQuizPresenter(TakeQuizViewModel takeQuizViewModel,
                             ViewManagerModel viewManagerModel,
                             ViewQuizView viewQuizView) {
        this.takeQuizViewModel = takeQuizViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewQuizView = viewQuizView;
    }

    @Override
    public void prepareSuccessView(TakeQuizOutputData response) {
//  TODO
    }

    @Override
    public void prepareFailView(String error) {

    }
//  TODO
}
