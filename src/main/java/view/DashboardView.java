package view;

import entity.Quiz;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class DashboardView extends JPanel implements PropertyChangeListener {

    private final String viewName = "dashboard";
    private final DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private LogoutController logoutController;

    private final JLabel usernameLabel;
    private final JPanel quizzesPanel = new JPanel();
    private final JLabel titleLabel = new JLabel("Dashboard - Your Quizzes", SwingConstants.CENTER);

    private final JButton logOutButton;
    private final JButton createQuizButton;

    public DashboardView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title Section
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Quizzes Section
        quizzesPanel.setLayout(new GridLayout(0, 2, 10, 10));
        quizzesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(new JScrollPane(quizzesPanel), BorderLayout.CENTER);

        // Buttons and User Info
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        usernameLabel = new JLabel("Currently logged in: ");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logOutButton = new JButton("Log Out");
        logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOutButton.addActionListener(evt -> {
            DashboardState currentState = dashboardViewModel.getState();
            if (logoutController != null) {
                logoutController.execute(currentState.getUsername());
            }
        });

        createQuizButton = new JButton("Create Quiz");
        createQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createQuizButton.addActionListener(evt -> {
            if (dashboardController != null) {
                dashboardController.switchToCreateQuizView();
            }
        });

        userPanel.add(usernameLabel);
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(logOutButton);
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(createQuizButton);

        add(userPanel, BorderLayout.SOUTH);

        // Initialize with quizzes
        updateQuizzes(dashboardViewModel.getState().getQuizzes());
    }

    /**
     * React to property changes in the ViewModel.
     *
     * @param evt the PropertyChangeEvent to react to
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            DashboardState newState = (DashboardState) evt.getNewValue();
            usernameLabel.setText("Currently logged in: " + newState.getUsername());
            updateQuizzes(newState.getQuizzes());
        }
    }

    /**
     * Update the quizzes displayed in the dashboard.
     *
     * @param quizzes the list of quizzes and their scores
     */
    private void updateQuizzes(List<Quiz> quizzes) {
        quizzesPanel.removeAll();

        for (Quiz quiz : quizzes) {
            JLabel quizLabel = new JLabel(quiz.getName());
            quizLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            quizzesPanel.add(quizLabel);

            JLabel scoreLabel = new JLabel("Not Available"); // Default score
            quizzesPanel.add(scoreLabel);
        }

        quizzesPanel.revalidate();
        quizzesPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }


    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}

