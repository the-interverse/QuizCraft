package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import entity.Quiz;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.view_quiz.ViewQuizViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.util.*;

/**
 * The Presenter for the Dashboard Use Case.
 */
public class DashboardPresenter implements DashboardOutputBoundary {

    private final DashboardViewModel dashboardViewModel;
    private final CreateQuizViewModel createQuizViewModel;
    private final ViewQuizViewModel viewQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private List<Map<String, Object>> questionsAndOptions = new ArrayList<>();


    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel,
                              CreateQuizViewModel createQuizViewModel, ViewQuizViewModel viewQuizViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.createQuizViewModel = createQuizViewModel;
        this.viewQuizViewModel = viewQuizViewModel;
        Map<String, Object> question1 = new HashMap<>();
        question1.put("question", "What is the purpose of version control in software development?");
        question1.put("answers", Arrays.asList("Version control is used to manage and track changes made to software code over time, allowing developers to collaborate effectively and maintain a history of modifications", "It is a way to create multiple versions of a software project, with each version having its own unique features and functionality", "Version control is a method to backup and restore software code in case of accidental deletions or modifications", "It is a tool for managing different branches of a software project, enabling developers to work on multiple features simultaneously"));
        question1.put("correctAnswer", 0);
        this.questionsAndOptions.add(question1);
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        List<String> quizzes = outputData.getQuizzes();
        DashboardState state = dashboardViewModel.getState();
        state.setQuizzes(quizzes);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DashboardState state = dashboardViewModel.getState();
        state.setErrorMessage(errorMessage);
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }
    @Override
    public void switchToCreateQuizView() {
        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void switchToViewQuizView() {
        viewManagerModel.setState(viewQuizViewModel.getViewName());
        viewQuizViewModel.getState().setQuizQuestionsAndOptions(questionsAndOptions);
        viewQuizViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

}
