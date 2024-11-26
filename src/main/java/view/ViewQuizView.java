package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import interface_adapter.view_quiz.ViewQuizViewModel;
import interface_adapter.view_quiz.ViewQuizState;
import interface_adapter.view_quiz.ViewQuizController;

public class ViewQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private ViewQuizController viewQuizController;
    private final String viewName = "ViewQuizView";
    private final ViewQuizViewModel viewQuizViewModel;
    private final ViewQuizState state;
    private JButton returnButton;
    private JButton takeQuizButton;
    private JLabel quizNameLabel;
    private JButton submitQuiz;
    private final List<JRadioButton> optionButtons = new ArrayList<>();
    private final Map<Integer, Integer> userSelections = new HashMap<>();
    private List<Map<String, Object>> questionDataList;

    public ViewQuizView(ViewQuizViewModel viewModel) {
        this.viewQuizViewModel = viewModel;
        this.viewQuizViewModel.addPropertyChangeListener(this);
        this.state = viewModel.getState();
        viewQuizUI();
    }

    public void viewQuizUI() {
        String quizName = state.getQuizName();
        String takingQuizStatus;
        questionDataList = state.getQuizQuestionsAndOptions();
        setLayout(new BorderLayout());
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        if (state.getTakingQuizStatus()) takingQuizStatus = "(Quiz In Progress)";
        else takingQuizStatus = "(Read Only)";
        String header = quizName + " " + takingQuizStatus;
        quizNameLabel = new JLabel(header);
        quizNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        quizNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizPanel.add(quizNameLabel);
        quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        int questionIndex = 0;
        for (Map<String, Object> questionData : questionDataList) {
            String questionText = (String) questionData.get("question");
            @SuppressWarnings("unchecked")
            List<String> options = (List<String>) questionData.get("answers");
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel questionLabel = new JLabel(questionText);
            questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ButtonGroup buttonGroup = new ButtonGroup();
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        JScrollPane scrollPane = new JScrollPane(quizPanel);
        add(scrollPane, BorderLayout.CENTER);
        returnButton = new JButton("Return to Dashboard");
        takeQuizButton = new JButton("Take Quiz");
        submitQuiz = new JButton("Submit Quiz");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(returnButton);
        buttonPanel.add(takeQuizButton);
        returnButton.addActionListener(evt -> viewQuizController.switchToDashboardView());
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
        });
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
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
            ViewQuizState state = viewQuizViewModel.getState();
            viewQuizViewModel.setState(state);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewQuizState state = (ViewQuizState) evt.getNewValue();
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewQuizController(ViewQuizController viewQuizController) {
        this.viewQuizController = viewQuizController;
    }
}