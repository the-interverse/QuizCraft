package interface_adapter.create_quiz;

import interface_adapter.ViewModel;

public class CreateQuizViewModel extends ViewModel<CreateQuizState> {
    public static final String TITLE_LABEL = "Create Quiz";
    public static final String QUIZ_NAME_LABEL = "Quiz Name:";
    public static final String QUESTION_AMOUNT_LABEL = "Amount of Questions:";
    public static final String DIFFICULTY_LABEL = "Difficulty";
    public static final String UPLOAD_PDF_BUTTON_LABEL = "Upload PDF";
    public static final String CREATE_BUTTON_LABEL = "Create";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public CreateQuizViewModel() {
        super("create quiz");
        setState(new CreateQuizState());
    }
}
