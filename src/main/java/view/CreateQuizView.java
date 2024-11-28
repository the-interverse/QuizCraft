package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Enumeration;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginState;

public class CreateQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private CreateQuizController createQuizController;
    private final String viewName = "createQuiz";
    private final CreateQuizViewModel createQuizViewModel;
    private final DashboardViewModel dashboardViewModel;
    private JTextField quizNameField;
    private JTextField questionAmountField;
    private ButtonGroup difficultyGroup;
    private JLabel pdfLabel;
    private JButton createButton;
    private JButton cancelButton;

    public CreateQuizView(CreateQuizViewModel viewModel, DashboardViewModel dashboardViewModel) {
        this.createQuizViewModel = viewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.createQuizViewModel.addPropertyChangeListener(this);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Create Quiz");

        JLabel quizNameLabel = new JLabel("Quiz Name:");
        quizNameField = new JTextField(20);

        JLabel questionAmountLabel = new JLabel("Amount of Questions:");
        questionAmountField = new JTextField(5);

        JLabel difficultyLabel = new JLabel("Difficulty:");
        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);
        easyButton.setSelected(true);

        JButton pdfUploadButton = new JButton("Upload PDF");
        pdfLabel = new JLabel("No file selected");
        pdfUploadButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(pdfUploadButton)) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
                            if (fileChooser.showOpenDialog(CreateQuizView.this) == JFileChooser.APPROVE_OPTION) {
                                File selectedPDF = fileChooser.getSelectedFile();
                                pdfLabel.setText(selectedPDF.getName());
                                CreateQuizState state = createQuizViewModel.getState();
                                state.setPdfFileName(selectedPDF.getAbsolutePath());
                                createQuizViewModel.setState(state);
                            }
                        }
                    }
        });

        createButton = new JButton("Create");
        cancelButton = new JButton("Return to Dashboard");
        createButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource() == createButton) {
                            final CreateQuizState currentState = createQuizViewModel.getState();
                            currentState.setDifficulty(getSelectedDifficulty());
                            createQuizController.execute(
                                    currentState.getQuizName(),
                                    currentState.getQuestionAmount(),
                                    currentState.getDifficulty(),
                                    currentState.getPdfFileName(),
                                    currentState.getUsername()
                            );
                            System.out.println(currentState.getUsername());
                        }
                    }
                });
        cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource() == cancelButton) {
                            resetFields();
                            createQuizController.switchToDashboardView(dashboardViewModel.getState().getUsername());
                        }
                    }
                });

        quizNameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateQuizName(); }
            public void removeUpdate(DocumentEvent e) { updateQuizName(); }
            public void changedUpdate(DocumentEvent e) { updateQuizName(); }
            private void updateQuizName() {
                CreateQuizState state = createQuizViewModel.getState();
                state.setQuizName(quizNameField.getText());
                createQuizViewModel.setState(state);
            }
        });

        questionAmountField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateQuestionAmount(); }
            public void removeUpdate(DocumentEvent e) { updateQuestionAmount(); }
            public void changedUpdate(DocumentEvent e) { updateQuestionAmount(); }
            private void updateQuestionAmount() {
                CreateQuizState state = createQuizViewModel.getState();
                state.setQuestionAmount(Integer.parseInt(questionAmountField.getText()));
                createQuizViewModel.setState(state);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(titleLabel);
        add(quizNameLabel);
        add(quizNameField);
        add(questionAmountLabel);
        add(questionAmountField);
        add(difficultyLabel);
        add(easyButton);
        add(mediumButton);
        add(hardButton);
        add(pdfUploadButton);
        add(pdfLabel);
        add(createButton);
        add(cancelButton);
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == createButton) {
            final CreateQuizState currentState = createQuizViewModel.getState();
            currentState.setDifficulty(getSelectedDifficulty());
            createQuizController.execute(
                    currentState.getQuizName(),
                    currentState.getQuestionAmount(),
                    currentState.getDifficulty(),
                    currentState.getPdfFileName(),
                    currentState.getUsername()
            );
//            viewModel.setState(state); // Trigger quiz creation via ViewModel updates
        } else if (evt.getSource() == cancelButton) {
            resetFields();
            createQuizController.switchToDashboardView(createQuizViewModel.getState().getUsername());
        }
    }

    private void resetFields() {
        quizNameField.setText("");
        questionAmountField.setText("");
        difficultyGroup.clearSelection();
        pdfLabel.setText("No file selected");
        CreateQuizState state = createQuizViewModel.getState();
        state.setQuizName("");
        state.setQuestionAmount(0);
        state.setDifficulty("");
        state.setPdfFileName("No file selected");
        createQuizViewModel.setState(state);
    }

    private String getSelectedDifficulty() {
        for (Enumeration<AbstractButton> buttons = difficultyGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateQuizState state = (CreateQuizState) evt.getNewValue();
        quizNameField.setText(state.getQuizName());
        questionAmountField.setText(String.valueOf(state.getQuestionAmount()));
        pdfLabel.setText(state.getPdfFileName());
    }

    public String getViewName() {
        return viewName;
    }

    public void setCreateQuizController(CreateQuizController createQuizController) {
        this.createQuizController = createQuizController;
    }
}
