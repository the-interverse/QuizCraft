package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ai_access.CohereAPI;
import data_access.DBUserDataAccessObject;
import entity.QuizFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizPresenter;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_quiz.ViewQuizController;
import interface_adapter.view_quiz.ViewQuizPresenter;
import interface_adapter.view_quiz.ViewQuizViewModel;
import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInteractor;
import use_case.create_quiz.CreateQuizOutputBoundary;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.view_quiz.ViewQuizInputBoundary;
import use_case.view_quiz.ViewQuizInteractor;
import use_case.view_quiz.ViewQuizOutputBoundary;
import view.CreateQuizView;
import view.DashboardView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import view.ViewQuizView;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new UserFactory();
    private final QuizFactory quizFactory = new QuizFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final DBUserDataAccessObject programDataAccessObject = new DBUserDataAccessObject(new UserFactory());
    private final CohereAPI aiAccessObject = new CohereAPI();

    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private LoginViewModel loginViewModel;
    private LoginView loginView;

    private DashboardViewModel dashboardViewModel;
    private DashboardView dashboardView;

    private CreateQuizViewModel createQuizViewModel;
    private CreateQuizView createQuizView;

    private ViewQuizViewModel viewQuizViewModel;
    private ViewQuizView viewQuizView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        dashboardViewModel = new DashboardViewModel();
        dashboardView = new DashboardView(dashboardViewModel);
        cardPanel.add(dashboardView, dashboardView.getViewName());
        return this;
    }

    /**
     * Adds the Create Quiz View to the application.
     * @return this builder
     */
    public AppBuilder addCreateQuizView() {
        createQuizViewModel = new CreateQuizViewModel();
        createQuizView = new CreateQuizView(createQuizViewModel, dashboardViewModel);
        cardPanel.add(createQuizView, createQuizView.getViewName());
        return this;
    }

    /**
     * Adds the View Quiz View to the application.
     * @return this builder
     */
    public AppBuilder addViewQuizView() {
        viewQuizViewModel = new ViewQuizViewModel();
        viewQuizView = new ViewQuizView(viewQuizViewModel);
        cardPanel.add(viewQuizView, viewQuizView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                programDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInUseCase() {
        final DashboardOutputBoundary dashboardOutputBoundary =
            new DashboardPresenter(viewManagerModel, dashboardViewModel,
                createQuizViewModel, viewQuizViewModel);
        final DashboardInputBoundary dashboardInteractor =
            new DashboardInteractor(programDataAccessObject, dashboardOutputBoundary);

        final DashboardController dashboardController = new DashboardController(dashboardInteractor, viewManagerModel);

        final LogoutController logoutController = new LogoutController(new LogoutInteractor(programDataAccessObject,
                new LogoutPresenter(viewManagerModel, dashboardViewModel, loginViewModel)));

        dashboardView.setDashboardController(dashboardController);
        dashboardView.setLogoutController(logoutController);

        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                dashboardViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                programDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                dashboardViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(programDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        dashboardView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the Create Quiz Use Case to the application.
     * @return this builder
     */
    public AppBuilder addCreateQuizUseCase() {
        final CreateQuizOutputBoundary createQuizOutputBoundary = new CreateQuizPresenter(viewManagerModel,
                viewQuizViewModel, createQuizViewModel, dashboardViewModel);
        final CreateQuizInputBoundary createQuizInteractor =
                new CreateQuizInteractor(createQuizOutputBoundary,
                    programDataAccessObject, quizFactory, aiAccessObject);

        final CreateQuizController createQuizController = new CreateQuizController(createQuizInteractor);
        createQuizView.setCreateQuizController(createQuizController);
        return this;
    }

    /**
     * Adds the View Quiz Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewQuizUseCase() {
        final ViewQuizOutputBoundary viewQuizOutputBoundary = new ViewQuizPresenter(viewManagerModel,
                dashboardViewModel, viewQuizViewModel);
        final ViewQuizInputBoundary viewQuizInteractor =
                new ViewQuizInteractor(viewQuizOutputBoundary);

        final ViewQuizController viewQuizController = new ViewQuizController(viewQuizInteractor);
        viewQuizView.setViewQuizController(viewQuizController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("QuizCraft @ 1982");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);
        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
