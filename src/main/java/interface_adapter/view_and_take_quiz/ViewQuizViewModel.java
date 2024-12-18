package interface_adapter.view_and_take_quiz;

import interface_adapter.ViewModel;

public class ViewQuizViewModel extends ViewModel<ViewQuizState> {
    public static final String TITLE_LABEL = "View Quiz";
    public static final String QUIZ_NAME_LABEL = "Quiz Name:";

    public ViewQuizViewModel() {
        super("ViewQuizView");
        setState(new ViewQuizState());
    }
}
