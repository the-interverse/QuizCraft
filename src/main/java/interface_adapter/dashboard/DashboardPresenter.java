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
    //private List<Map<String, Object>> questionsAndOptions = new ArrayList<>();


    public DashboardPresenter(ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel,
                              CreateQuizViewModel createQuizViewModel, ViewQuizViewModel viewQuizViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.createQuizViewModel = createQuizViewModel;
        this.viewQuizViewModel = viewQuizViewModel;
        Map<String, Object> question1 = new HashMap<>();

        Map<String, Object> question2 = new HashMap<>();

        Map<String, Object> question3 = new HashMap<>();
        question1.put("question", "What is the purpose of version control in software development?");
        question1.put("answers", Arrays.asList("Version control is used to manage and track changes made to software code over time, allowing developers to collaborate effectively and maintain a history of modifications", "It is a way to create multiple versions of a software project, with each version having its own unique features and functionality", "Version control is a method to backup and restore software code in case of accidental deletions or modifications", "It is a tool for managing different branches of a software project, enabling developers to work on multiple features simultaneously"));
        question1.put("correctAnswer", 0);
        question2.put("question", "Which command is used to add changes to the next commit in Git?");
        question2.put("answers", Arrays.asList("git add", "git commit", "git push", "git pull"));
        question2.put("correctAnswer", 0);

        question3.put("question", "What does the 'git status' command do?");
        question3.put("answers", Arrays.asList("It displays the current branch and the latest commit message", "It shows the differences between the working directory and the staged files", "It provides information about the status of files in the repository, indicating if they are untracked, tracked, staged, or modified", "It lists all the files in the repository and their corresponding commit history"));
        question3.put("correctAnswer", 2);
      //  this.questionsAndOptions.add(question1);

//        this.questionsAndOptions.add(question2);

    //    this.questionsAndOptions.add(question3);
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
    public void switchToViewQuizView(List<Map<String, Object>> quizData, String quizName) {
        viewManagerModel.setState(viewQuizViewModel.getViewName());
        viewQuizViewModel.getState().setQuizQuestionsAndOptions(quizData);
        viewQuizViewModel.getState().setQuizName(quizName);
        viewQuizViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

}
