package QuizUIGenerator;

import com.google.gson.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class RetrieveQuiz {

    private static final String PROJECT_ID = "quizcraft-eb0a5";
    private static final String API_KEY = "AIzaSyA7152q13udjy3Z5mxNNH1BX7EkmE2GFCQ";

    public RetrieveQuiz() {}

    public JComponent getQuizListComponent() {
        Map<String, String> quizMap = null;
        try {
            quizMap = getQuizNames();
        } catch (Exception e) {
            e.printStackTrace();
            quizMap = new HashMap<>();
        }

        return createQuizListComponent(quizMap);
    }

    private Map<String, String> getQuizNames() throws Exception {
        String urlString = "https://firestore.googleapis.com/v1/projects/"
                + PROJECT_ID + "/databases/(default)/documents/quizzes?key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(content.toString(), JsonObject.class);

        Map<String, String> quizMap = new HashMap<>();

        if (jsonResponse.has("documents")) {
            JsonArray documents = jsonResponse.getAsJsonArray("documents");
            for (JsonElement docElement : documents) {
                JsonObject document = docElement.getAsJsonObject();
                String name = document.get("name").getAsString();
                String documentId = name.substring(name.lastIndexOf('/') + 1);
                JsonObject fields = document.getAsJsonObject("fields");
                if (fields != null && fields.has("Quiz Name")) {
                    String quizName = fields.getAsJsonObject("Quiz Name")
                            .get("stringValue").getAsString();
                    quizMap.put(documentId, quizName);
                    System.out.println("Found Quiz ID: " + documentId + ", Name: " + quizName);
                }
            }
        }

        return quizMap;
    }

    private JComponent createQuizListComponent(Map<String, String> quizMap) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            String documentId = entry.getKey();
            String quizName = entry.getValue();

            JPanel quizPanel = new JPanel();
            quizPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel titleLabel = new JLabel(quizName);
            JButton viewButton = new JButton("View Quiz");

            viewButton.addActionListener(e -> {
                try {
                    String quizData = getQuizData(documentId);
                    System.out.println("Retrieved Quiz JSON: " + quizData);
                    QuizUI quizUI = new QuizUI();
                    quizUI.createQuizUI(quizData);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            quizPanel.add(titleLabel);
            quizPanel.add(viewButton);
            panel.add(quizPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        return scrollPane;
    }

    private String getQuizData(String documentId) throws Exception {
        String urlString = "https://firestore.googleapis.com/v1/projects/" + PROJECT_ID
                + "/databases/(default)/documents/quizzes/" + documentId + "?key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(content.toString(), JsonObject.class);

        JsonObject fields = jsonResponse.getAsJsonObject("fields");
        if (fields == null) {
            throw new Exception("No fields found in the quiz data.");
        }

        String quizName = "";
        if (fields.has("Quiz Name")) {
            quizName = fields.getAsJsonObject("Quiz Name").get("stringValue").getAsString();
        }

        List<JsonObject> questionList = new ArrayList<>();

        if (fields.has("Quiz Questions")) {
            JsonObject quizQuestions = fields.getAsJsonObject("Quiz Questions");

            if (quizQuestions.has("arrayValue")) {
                JsonArray questionsArray = quizQuestions.getAsJsonObject("arrayValue")
                        .getAsJsonArray("values");
                if (questionsArray != null) {
                    for (JsonElement questionElement : questionsArray) {
                        JsonObject questionMapValue = questionElement.getAsJsonObject()
                                .getAsJsonObject("mapValue");
                        if (questionMapValue != null) {
                            JsonObject questionFields = questionMapValue.getAsJsonObject("fields");
                            processQuestionFields(questionFields, questionList);
                        }
                    }
                }
            } else {
                throw new Exception("'Quiz Questions' must be an arrayValue. Please ensure your data is consistent.");
            }
        }

        JsonObject simplifiedQuiz = new JsonObject();
        simplifiedQuiz.addProperty("Quiz Name", quizName);

        JsonArray simplifiedQuestionsArray = new JsonArray();
        for (JsonObject q : questionList) {
            simplifiedQuestionsArray.add(q);
        }
        simplifiedQuiz.add("Quiz Questions", simplifiedQuestionsArray);

        String simplifiedQuizJson = gson.toJson(simplifiedQuiz);

        System.out.println("Simplified Quiz JSON: " + simplifiedQuizJson);

        return simplifiedQuizJson;
    }

    private void processQuestionFields(JsonObject questionFields, List<JsonObject> questionList) {
        String questionText = "";
        if (questionFields.has("question")) {
            questionText = questionFields.getAsJsonObject("question").get("stringValue").getAsString();
        }

        List<String> answers = new ArrayList<>();
        if (questionFields.has("answers")) {
            JsonObject answersObject = questionFields.getAsJsonObject("answers");
            JsonArray answersArray = answersObject.getAsJsonObject("arrayValue")
                    .getAsJsonArray("values");

            if (answersArray != null) {
                for (JsonElement answerElement : answersArray) {
                    String answer = answerElement.getAsJsonObject().get("stringValue").getAsString();
                    answers.add(answer);
                }
            }
        }

        String correctAnswer = "";
        if (questionFields.has("correctAnswer")) {
            correctAnswer = questionFields.getAsJsonObject("correctAnswer")
                    .get("stringValue").getAsString();
        }

        JsonObject simplifiedQuestion = new JsonObject();
        simplifiedQuestion.addProperty("question", questionText);

        JsonArray simplifiedAnswersArray = new JsonArray();
        for (String ans : answers) {
            simplifiedAnswersArray.add(ans);
        }
        simplifiedQuestion.add("answers", simplifiedAnswersArray);
        simplifiedQuestion.addProperty("correctAnswer", correctAnswer);

        questionList.add(simplifiedQuestion);
    }
}
