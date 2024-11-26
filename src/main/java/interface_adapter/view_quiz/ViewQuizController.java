package interface_adapter.view_quiz;

import use_case.view_quiz.ViewQuizInputBoundary;
import use_case.view_quiz.ViewQuizInputData;

import java.util.List;
import java.util.Map;

public class ViewQuizController {
    private final ViewQuizInputBoundary viewQuizInteractor;

    public ViewQuizController(ViewQuizInputBoundary viewQuizInteractor) {
        this.viewQuizInteractor = viewQuizInteractor;
    }

    public void switchToDashboardView() {
        viewQuizInteractor.switchToDashboardView();
    }

    public void execute(List<Map<String, Object>> questionsAndOptions, String username, String quizName){
        final ViewQuizInputData viewQuizInputData = new ViewQuizInputData(questionsAndOptions, username, quizName);
        viewQuizInteractor.execute(viewQuizInputData);
    }
}
