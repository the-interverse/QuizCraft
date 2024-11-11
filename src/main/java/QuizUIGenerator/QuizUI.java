package QuizUIGenerator;

import com.google.gson.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class QuizUI {

    private JFrame frame;

    public QuizUI() {
        frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout());
    }

    public void createQuizUI(String jsonInput) {
        Map<String, Object> quizData = parseJson(jsonInput);

        String quizName = (String) quizData.get("Quiz Name");
        List<Map<String, Object>> questions = (List<Map<String, Object>>) quizData.get("Quiz Questions");

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        JLabel quizNameLabel = new JLabel(quizName);
        quizNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        quizNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizPanel.add(quizNameLabel);
        quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Map<String, String> userSelections = new HashMap<>();

        for (Map<String, Object> questionMap : questions) {
            String questionText = (String) questionMap.get("question");
            String[] options = ((String) questionMap.get("answers")).split(",");

            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel questionLabel = new JLabel("Q: " + questionText);
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
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton returnButton = new JButton("Return to Dashboard");
        returnButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(returnButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private Map<String, Object> parseJson(String jsonInput) {
        Map<String, Object> data = new HashMap<>();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonInput, JsonObject.class);

        String quizName = jsonObject.get("Quiz Name").getAsString();
        data.put("Quiz Name", quizName);

        JsonArray questionsArray = jsonObject.getAsJsonArray("Quiz Questions");

        List<Map<String, Object>> questionsList = new ArrayList<>();

        for (JsonElement questionElement : questionsArray) {
            JsonObject questionObject = questionElement.getAsJsonObject();

            String questionText = questionObject.get("question").getAsString();

            JsonArray answersArray = questionObject.getAsJsonArray("answers");
            List<String> answersList = new ArrayList<>();
            for (JsonElement answerElement : answersArray) {
                answersList.add(answerElement.getAsString());
            }

            String answersJoined = String.join(",", answersList);

            String correctAnswer = questionObject.get("correctAnswer").getAsString();

            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("question", questionText);
            questionMap.put("answers", answersJoined);
            questionMap.put("correctAnswer", correctAnswer);

            questionsList.add(questionMap);
        }

        data.put("Quiz Questions", questionsList);
        return data;
    }

}
