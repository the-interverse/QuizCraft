package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.logout.LogoutController;

public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final DashboardViewModel dashboardViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private LogoutController logoutController;
    private DashboardController dashboardController;

    private final JLabel username;

    private final JButton logOut;
    private final JButton toLogin;

    public LoggedInView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

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
                final DashboardState currentState = dashboardViewModel.getState();
                logoutController.execute(currentState.getUsername());
            }
        });

        toLogin.addActionListener(evt -> dashboardController.switchToCreateQuizView());

        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final DashboardState state = (DashboardState) evt.getNewValue();
            username.setText(state.getUsername());
        } else if (evt.getPropertyName().equals("password")) {
            final DashboardState state = (DashboardState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setLoggedInController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}
