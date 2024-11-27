package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import interface_adapter.dashboard.DashboardState;
import interface_adapter.view_quiz.ViewQuizViewModel;
import interface_adapter.view_quiz.ViewQuizState;
import interface_adapter.view_quiz.ViewQuizController;

public class ViewQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private ViewQuizController viewQuizController;
    private final String viewName = "ViewQuizView";
    private final ViewQuizViewModel viewQuizViewModel;
    private ViewQuizState state;
    private JButton returnButton;
    private JButton takeQuizButton;
    private JLabel quizNameLabel;
    private JButton submitQuiz;
    private JButton tryAgain;
    private final List<JRadioButton> optionButtons = new ArrayList<>();
    private final Map<Integer, Integer> userSelections = new HashMap<>();
    private List<Map<String, Object>> questionDataList;
    private JPanel quizPanel;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private String quizName;

    public ViewQuizView(ViewQuizViewModel viewModel) {
        this.viewQuizViewModel = viewModel;
        this.viewQuizViewModel.addPropertyChangeListener(this);
        this.state = viewModel.getState();
    }

    public void initUI() {
        resetUI();
        setLayout(new BorderLayout());
        quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(quizPanel);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
        viewQuizUI();
    }

    public void resetUI() {
        removeAll();
        optionButtons.clear();
        userSelections.clear();

        quizName = state.getQuizName();
        quizNameLabel = new JLabel(quizName + " (Read Only)");
        quizNameLabel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));

        returnButton = new JButton("Return to Dashboard");
        takeQuizButton = new JButton("Take Quiz");
        submitQuiz = new JButton("Submit Quiz");
        tryAgain = new JButton("Try Again");

        returnButton.setVisible(true);
        takeQuizButton.setVisible(true);
        submitQuiz.setEnabled(false);
        submitQuiz.setVisible(false);
        tryAgain.setVisible(false);


        for (JRadioButton button : optionButtons) {
            //button.setEnabled(true);
        }

        revalidate();
        repaint();
    }

    public void viewQuizUI() {
        questionDataList = state.getQuizQuestionsAndOptions();
        quizNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        quizNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quizPanel.add(quizNameLabel);
        quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        int questionIndex = 0;
        for (Map<String, Object> questionData : questionDataList) {
            String questionText = (String) questionData.get("question");
            List<String> options = (List<String>) questionData.get("answers");
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10));
            JLabel questionLabel = new JLabel(questionText);
            questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            ButtonGroup buttonGroup = new ButtonGroup();
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            int optionIndex = 0;
            for (String option : options) {
                JRadioButton optionButton = new JRadioButton(option.trim());
                int finalOptionIndex = optionIndex;
                int finalQuestionIndex = questionIndex;
                optionButton.addActionListener(e -> {
                    userSelections.put(finalQuestionIndex, finalOptionIndex);
                    if (userSelections.size() == questionDataList.size()) {
                        submitQuiz.setEnabled(true);
                    }
                });
                optionButton.setFont(new Font("Arial", Font.PLAIN, 14));
                optionButton.setEnabled(false);
                buttonGroup.add(optionButton);
                buttonsPanel.add(optionButton);
                optionButtons.add(optionButton);
                optionIndex++;
            }
            questionPanel.add(questionLabel);
            questionPanel.add(buttonsPanel);
            quizPanel.add(questionPanel);
            quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            questionIndex++;
        }

        buttonPanel.add(returnButton);
        buttonPanel.add(takeQuizButton);
        buttonPanel.add(tryAgain);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        returnButton.addActionListener(evt -> viewQuizController.switchToDashboardView());
        tryAgain.addActionListener(evt -> initUI());

        takeQuizButton.addActionListener(evt -> {
            if (evt.getSource().equals(takeQuizButton)) {
                userSelections.clear();
                final ViewQuizState currentState = viewQuizViewModel.getState();
                currentState.setTakingQuizStatus(true);
                quizNameLabel.setText(quizName + " (Quiz In Progress)");
                returnButton.setVisible(false);
                takeQuizButton.setVisible(false);
                buttonPanel.add(submitQuiz);
                for (JRadioButton button : optionButtons) {
                    button.setEnabled(true);
                }
                submitQuiz.setVisible(true);
                submitQuiz.setEnabled(false);
            }
        });
        submitQuiz.addActionListener(evt -> {
            submitQuiz.setVisible(false);
            quizNameLabel.setText(quizName + " (Results)");
            int correctAnswers = calculateScore();
            int totalQuestions = questionDataList.size();
            int percentage = (int) ((double) correctAnswers / totalQuestions * 100);
            String scoreText = "Your score: " + correctAnswers + "/" + totalQuestions + " (" + percentage + "%)";
            JOptionPane.showMessageDialog(null, scoreText, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
            returnButton.setVisible(true);
            tryAgain.setVisible(true);
        });
    }

    private int calculateScore() {
        int correctAnswers = 0;
        for (int questionIndex = 0; questionIndex < questionDataList.size(); questionIndex++) {
            Map<String, Object> questionData = questionDataList.get(questionIndex);
            int correctAnswer = (Integer) questionData.get("correctAnswer");
            if (userSelections.getOrDefault(questionIndex, -1) == correctAnswer) {
                correctAnswers++;
            }
            List<String> options = (List<String>) questionData.get("answers");
            for (int optionIndex = 0; optionIndex < options.size(); optionIndex++) {
                JRadioButton button = optionButtons.get(questionIndex * options.size() + optionIndex);
                button.setEnabled(false);
                if (optionIndex == correctAnswer) {
                    button.setBackground(Color.GREEN);
                }
            }
        }
        return correctAnswers;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == returnButton) {
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ("state".equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(() -> {
                ViewQuizState newState = (ViewQuizState) evt.getNewValue();
                state = newState;
                questionDataList = state.getQuizQuestionsAndOptions();
                initUI();
                revalidate();
                repaint();
            });
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewQuizController(ViewQuizController viewQuizController) {
        this.viewQuizController = viewQuizController;
    }
}
