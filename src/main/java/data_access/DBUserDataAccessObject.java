package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Quiz;
import entity.QuizQuestion;
import entity.User;
import entity.UserFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.create_quiz.CreateQuizDataAccessInterface;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        CreateQuizDataAccessInterface,
        DashboardDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String USERNAME = "username";
    private static final String FIELDS = "fields";
    private static final String STRINGVALUE = "stringValue";
    private static final String QUIZNAME = "quizName";
    private static final String QUESTION = "question";
    private static final String CORRECTANSWER = "correctAnswer";
    private static final String ARRAYVALUE = "arrayValue";
    private static final String CONNECT = "-";
    private static final String ANSWERS = "answers";
    private static final String VALUES = "values";
    private static final String MAPVALUE = "mapValue";
    private static final String QUIZZES_URL = "quizzes/";

    private static final String PASSWORD = "password";
    private static final String QUIZ_QUESTIONS = "quizQuestions";
    private static final String MESSAGE = "message";
    private static final String STORAGE_URL =
        "https://firestore.googleapis.com/v1/projects/quizcraft-eb0a5/databases/(default)/documents/";
    private static final String PATH_TO_USERS = "users/";
    private final UserFactory userFactory;

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User get(String username) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(STORAGE_URL + PATH_TO_USERS + username)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject(FIELDS);
                final String name = userJSONObject.getJSONObject(USERNAME).getString(STRINGVALUE);
                final String password = userJSONObject.getJSONObject(PASSWORD).getString(STRINGVALUE);

                return userFactory.create(name, password);
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setCurrentUsername(String name) {
        // this isn't implemented for the lab
    }

    @Override
    public boolean existsByName(String username) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(STORAGE_URL + PATH_TO_USERS + username)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            return response.code() == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject(String
            .format("{\"fields\": {\"username\": {\"stringValue\": \"%s\"}, \"password\": {\"stringValue\": \"%s\"}}}",
                user.getUsername(), user.getPassword()));
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url(STORAGE_URL + PATH_TO_USERS + "?documentId=" + user.getUsername())
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                // success!
            }
            else {
                throw new RuntimeException(responseBody.getJSONObject("error").getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getCurrentUsername() {
        return null;
    }

    @Override
    public boolean quizExistsByName(String username, String quizName) {
        return false;
    }

    @Override
    public void saveQuiz(Quiz quiz, String username) {
        final String documentName = quiz.getName() + CONNECT + username;
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        final JSONObject fields = new JSONObject();
        requestBody.put(FIELDS, fields);

        // Add quiz name
        fields.put("quizName", new JSONObject().put(STRINGVALUE, quiz.getName()));

        // Add quiz questions
        final JSONArray quizQuestions = new JSONArray();
        for (QuizQuestion quizQuestion : quiz.getQuestions()) {
            final JSONObject quizQuestionJSON = new JSONObject();
            final JSONObject questionFields = new JSONObject();

            // Add question text
            questionFields.put(QUESTION, new JSONObject().put(STRINGVALUE, quizQuestion.getQuestion()));

            // Add correct answer index
            questionFields.put(CORRECTANSWER, new JSONObject().put("integerValue", quizQuestion.getCorrectIndex()));

            // Add answers array

            final JSONArray answers = new JSONArray();
            for (String answer : quizQuestion.getAnswers()) {
                answers.put(new JSONObject().put(STRINGVALUE, answer));
            }
            questionFields.put(ANSWERS, new JSONObject().put(ARRAYVALUE, new JSONObject().put(VALUES, answers)));

            quizQuestionJSON.put(FIELDS, questionFields);
            quizQuestions.put(new JSONObject().put(MAPVALUE, quizQuestionJSON));
        }

        fields.put(QUIZ_QUESTIONS, new JSONObject().put(ARRAYVALUE, new JSONObject().put(VALUES, quizQuestions)));

        // Add username as userID
        fields.put("userID", new JSONObject().put(STRINGVALUE, username));

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url(STORAGE_URL + QUIZZES_URL + "?documentId=" + documentName)
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                System.out.println("Success");
            }
            else {
                throw new RuntimeException(responseBody.getJSONObject("error").getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getQuizzes(String username) {
        final OkHttpClient client = new OkHttpClient();

        final String url = STORAGE_URL + QUIZZES_URL;

        final Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                final List<String> result = new ArrayList<>();
                final JSONArray documents = responseBody.getJSONArray("documents");
                for (int i = 0; i < documents.length(); i++) {
                    final JSONObject document = documents.getJSONObject(i);
                    final String name = document.getString("name");

                    // Extract only the document ID from the full path
                    final String documentId = name.substring(name.lastIndexOf('/') + 1);
                    final String[] parts = documentId.split(CONNECT);

                    if (parts[1].equals(username)) {
                        result.add(parts[0]);
                    }
                }
                return result;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Map<String, Object>> getQuizData(String username, String quizName) {
        List<Map<String, Object>> result = new ArrayList<>();
        final OkHttpClient client = new OkHttpClient();

        final String url = STORAGE_URL + QUIZZES_URL;

        final Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                final JSONArray documents = responseBody.getJSONArray("documents");
                for (int i = 0; i < documents.length(); i++) {
                    final JSONObject document = documents.getJSONObject(i);
                    final String name = document.getString("name");

                    // Extract only the document ID from the full path
                    final String documentId = name.substring(name.lastIndexOf('/') + 1);
                    final String[] parts = documentId.split(CONNECT);

                    if (parts[1].equals(username) && parts[0].equals(quizName)) {
                        final JSONObject quizJSONObject = document.getJSONObject(FIELDS)
                            .getJSONObject(QUIZ_QUESTIONS);
                        result = cleanUpFirebaseJson(quizJSONObject);
                        break;
                    }
                }
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    private static List<Map<String, Object>> cleanUpFirebaseJson(JSONObject jsonObject) {
        final JSONArray questions = jsonObject.getJSONObject(ARRAYVALUE).getJSONArray(VALUES);
        final List<Map<String, Object>> result = new ArrayList<>();
        for (Object question : questions) {
            final Map<String, Object> questionInfo = new HashMap<>();
            final String text = ((JSONObject) question).getJSONObject(MAPVALUE).getJSONObject(FIELDS)
                .getJSONObject(QUESTION).getString(STRINGVALUE);
            final Integer correctIndex = ((JSONObject) question).getJSONObject(MAPVALUE).getJSONObject(FIELDS)
                .getJSONObject(CORRECTANSWER).getInt("integerValue");
            final JSONArray answers = ((JSONObject) question).getJSONObject(MAPVALUE).getJSONObject(FIELDS)
                .getJSONObject(ANSWERS).getJSONObject(ARRAYVALUE).getJSONArray(VALUES);
            final List<String> answersText = new ArrayList<>();
            for (Object answer : answers) {
                answersText.add(((JSONObject) answer).getString(STRINGVALUE));
            }
            questionInfo.put(QUESTION, text);
            questionInfo.put(CORRECTANSWER, correctIndex);
            questionInfo.put(ANSWERS, answersText);
            result.add(questionInfo);
        }
        return result;
    }
}
