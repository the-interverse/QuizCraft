package view;

import QuizUIGenerator.RetrieveQuiz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.dashboard.LoggedInController;
import interface_adapter.dashboard.LoggedInState;
import interface_adapter.dashboard.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private LogoutController logoutController;
    private LoggedInController loggedInController;

    private final JLabel username;

    private final JButton logOut;
    private final JButton toLogin;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Dashboard");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        toLogin = new JButton("Create Quiz");
        buttons.add(toLogin);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        logOut.addActionListener(evt -> {
            if (evt.getSource().equals(logOut)) {
                final LoggedInState currentState = loggedInViewModel.getState();
                logoutController.execute(currentState.getUsername());
            }
        });

        toLogin.addActionListener(evt -> loggedInController.switchToCreateQuizView());

        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordErrorField);
        this.add(buttons);
//
//        RetrieveQuiz retrieveQuiz = new RetrieveQuiz();
//        JComponent quizListComponent = retrieveQuiz.getQuizListComponent();
//        this.add(quizListComponent);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        } else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }
}
