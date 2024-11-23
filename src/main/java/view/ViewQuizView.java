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
    private final ViewQuizViewModel viewModel;
    private final ViewQuizState state;
    private JButton returnButton;
    private List<Map<String, List<String>>> questionsAndOptions = new ArrayList<>();

    public ViewQuizView(ViewQuizViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.state = viewModel.getState();


        viewQuizUI();


    }

    public void viewQuizUI() {

        String quizName = state.getQuizName();
        List<Map<String, List<String>>> questions = state.getQuizQuestionsAndOptions();

        setLayout(new BorderLayout());
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        JLabel quizNameLabel = new JLabel(quizName);
        quizNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        quizNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizPanel.add(quizNameLabel);
        quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Map<String, String> userSelections = new HashMap<>();

        for (Map<String, List<String>> question : questions) {
            String questionText = question.keySet().iterator().next();
            List<String> options = question.get(questionText);

            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel questionLabel = new JLabel(questionText);
            questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ButtonGroup buttonGroup = new ButtonGroup();
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            for (String option : options) {
                JRadioButton optionButton = new JRadioButton(option.trim());
                optionButton.addActionListener(e -> {
                    userSelections.put(questionText, optionButton.getText());
                });

                buttonGroup.add(optionButton);
                buttonsPanel.add(optionButton);
            }

            questionPanel.add(questionLabel);
            questionPanel.add(buttonsPanel);
            quizPanel.add(questionPanel);
            quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(quizPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton returnButton = new JButton("Return to Dashboard");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(returnButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == returnButton) {
            ViewQuizState state = viewModel.getState();
            viewModel.setState(state);
        } else if (evt.getSource() == returnButton) {
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
