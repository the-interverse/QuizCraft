package interface_adapter.view_quiz;

import interface_adapter.ViewModel;

public class ViewQuizViewModel extends ViewModel<ViewQuizState> {
    public static final String TITLE_LABEL = "View Quiz";
    public static final String QUIZ_NAME_LABEL = "Quiz Name:";
    public static final String QUESTION_AMOUNT_LABEL = "Amount of Questions:";
    public static final String DIFFICULTY_LABEL = "Difficulty";

    public ViewQuizViewModel() {
        super("view quiz");
        setState(new ViewQuizState());
    }
}
