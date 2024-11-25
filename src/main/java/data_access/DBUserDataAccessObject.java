package data_access;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Quiz;
import entity.QuizQuestion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import entity.UserFactory;
import entity.User;
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
    private static final int ERROR_CODE = 400;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String QUIZNAME = "quizName";
    private static final String PASSWORD = "password";
    private static final String QUIZ_QUESTIONS = "quizQuestions";
    private static final String MESSAGE = "message";
    private static final String STORAGE_URL = "https://firestore.googleapis.com/v1/projects/quizcraft-eb0a5/databases/(default)/documents/";
    private static final String PATH_TO_USERS = "users/";
    private static final String RETURN_SECURE_TOKEN = "returnSecureToken";
    private static final String API_KEY = "AIzaSyA7152q13udjy3Z5mxNNH1BX7EkmE2GFCQ";
    private final UserFactory userFactory;
    private final Map<String, String> userTokenStorage = new HashMap<>();

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User get(String username) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(STORAGE_URL + PATH_TO_USERS + username)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("fields");
                final String name = userJSONObject.getJSONObject(USERNAME).getString("stringValue");
                final String password = userJSONObject.getJSONObject(PASSWORD).getString("stringValue");

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
        final RequestBody body = RequestBody.create(null, new byte[]{});
        final Request request = new Request.Builder()
                .url(STORAGE_URL + PATH_TO_USERS + username)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            return response.code() == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
// "https://quizcraft-eb0a5.firebaseio.com"
// "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key="
    @Override
    public void save(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
//        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject(String.format("{\"fields\": {\"username\": {\"stringValue\": \"%s\"}, \"password\": {\"stringValue\": \"%s\"}}}", user.getUsername(), user.getPassword()));
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
                throw new RuntimeException(responseBody.getJSONObject("error").getString("message"));
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
        String documentName = quiz.getName() + "-" + username;
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        JSONObject requestBody = new JSONObject();
        JSONObject fields = new JSONObject();
        requestBody.put("fields", fields);

        // Add quiz name
        fields.put("quizName", new JSONObject().put("stringValue", quiz.getName()));

        // Add quiz questions
        JSONArray quizQuestions = new JSONArray();
        for (QuizQuestion quizQuestion : quiz.getQuestions()) {
            JSONObject quizQuestionJSON = new JSONObject();
            JSONObject questionFields = new JSONObject();

            // Add question text
            questionFields.put("question", new JSONObject().put("stringValue", quizQuestion.getQuestion()));

            // Add correct answer index
            questionFields.put("correctAnswer", new JSONObject().put("integerValue", quizQuestion.getCorrectIndex()));

            // Add answers array
            JSONArray answers = new JSONArray();
            for (String answer : quizQuestion.getAnswers()) {
                answers.put(new JSONObject().put("stringValue", answer));
            }
            questionFields.put("answers", new JSONObject().put("arrayValue", new JSONObject().put("values", answers)));

            quizQuestionJSON.put("fields", questionFields);
            quizQuestions.put(new JSONObject().put("mapValue", quizQuestionJSON));
        }

        fields.put("quizQuestions", new JSONObject().put("arrayValue", new JSONObject().put("values", quizQuestions)));

        // Add username as userID
        fields.put("userID", new JSONObject().put("stringValue", username));

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url(STORAGE_URL + "quizzes/" + "?documentId=" + documentName)
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
//                success
            }
            else {
                throw new RuntimeException(responseBody.getJSONObject("error").getString("message"));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Quiz> getQuizzes(String username) {
        OkHttpClient client = new OkHttpClient();

        String url = STORAGE_URL + "quizzes/" + ":listCollectionIds";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == SUCCESS_CODE) {
                return List.of();
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
