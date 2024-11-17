package use_case.create_quiz;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;

import entity.Quiz;
import entity.QuizQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CohereClient {
    private final Cohere cohere;

    public CohereClient(String apiKey) {

        this.cohere = Cohere.builder()
                .token(apiKey)
                .clientName("QuizCraft")
                .build();

    }

    // Example Call:
        // CohereClient client = new CohereClient("the-api-key");
        // JSONObject quizJson = client.generateQuizJson("The very long string of course material", "Quiz Title", 5, "medium", 1000);
        // System.out.println(quizJson.toString(2)); // *Pretty* print the JSON :)

    // calls the API
    public Quiz generateQuestions(String prompt, int maxTokens, String quizTitle) throws IOException, JSONException, ParseException {
        ChatResponse response = cohere.v2()
                .chat(V2ChatRequest.builder()
                        .model("command-r-plus")
                        .messages(List.of(ChatMessageV2.user(
                                UserMessage.builder()
                                        .content(UserMessageContent.of(prompt))
                                        .build())))
                        .maxTokens(maxTokens)
                        .build());



        JSONObject object = (JSONObject) new JSONParser().parse(String.valueOf(response));
        JSONArray message = (JSONArray) object.get("message");
        JSONObject message0 = (JSONObject) message.get(0);
        JSONArray content = (JSONArray) message0.get("content");
        JSONObject content0 = (JSONObject) content.get(0);

        String responseText = content0.get("text").toString();
        return processApiResponse(responseText, quizTitle);
    }

    /*
    // method sends a request to Cohere API to generate questions based on the given prompt and the max
    // number of tokens. It constructs a JSON request body, sends the HTTP request and returns the
    // response as a string
    private String sendApiRequest(String prompt, int maxTokens) throws IOException {
        // convert the request parameters into a JSON-formatted string / payload
        MediaType mediaType = MediaType.parse("application/json");    // defines the media type of the content sent in the HTTP request; specifying "application/json" tells the server that the request body is JSON formatted, allowing the server to process it correctly
        JSONObject requestBody = new JSONObject();                                // initializes a new JSON object to hold the data being sent in the request
        requestBody.put("model", "command-xlarge-nightly");                       // specifies the model to use for generating the quiz
        requestBody.put("prompt", prompt);                                        // defines the prompt which will be given to the model
        requestBody.put("max_tokens", maxTokens);                                 // sets the max number of tokens a model can generate in its response (controls the length)
        requestBody.put("temperature", 0.7);                                      // controls the randomness or "creativity" of the model's response
        requestBody.put("k", 0);                                                  // adjusts top-k sampling
        requestBody.put("p", 0.75);                                               // controls the top-p (nucleus) sampling
        requestBody.put("frequency_penalty", 0);                                  // controls the likelihood of a model repeating words
        requestBody.put("presence_penalty", 0);                                   // discourages the modes from mentioning new topics or concepts that haven't been seen in the prompt
        requestBody.put("stop_sequences", new String[]{});                        // no stop sequences are set - the model generates output until the max_token limit is reached / finishes thought
        requestBody.put("return_likelihoods", "NONE");                            // configures the model to return likelihoods for each token

        // set up the request object, specifying the URL, HTTP method, headers, etc. 
        RequestBody body = RequestBody.create(mediaType, requestBody.toString()); // creates the body of the POST request
        Request request = new Request.Builder()                                   // used to construct the HTTP request. Provides a convenient way to set all properties of the request in a fluent manner. Can be used to specify the URL, HTTP method, headers, and body.
                .url(API_URL)                                                     // sets the destination URL for API request
                .post(body)                                                       // Specifies the HTTP method as POST and attaches the body containing the JSON payload
                .addHeader("Authorization", "BEARER " + apiKey)      // Adds an Authorization header to the request, which authenticates the user with the Cohere API (The Cohere API requires a token (your apiKey) to verify that ones has the permission to use the service)
                .addHeader("Content-Type", "application/json")       // specifies the type of content being sent
                .build();                                                         // completes the setup of the Request object

        // makes the request to the Cohere API and returns a response object containing the server's reply
        try (Response response = client.newCall(request).execute()) {
            if (!r esponse.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();                                      // extracts the body of the response and returns it as a string
        }
    }

    */

    public String createPrompt(String courseMaterial, String quizTitle, int numQuestions, String difficulty) {
        return String.format("Generate a %s quiz titled '%s' with %d multiple-choice questions based on the following course material. Each question should have four answer options numbered 0, 1, 2, and 3, and at the end of each question, specify the correct answer number in the format 'Answer: <number>'.\n%s",
                difficulty, quizTitle, numQuestions, courseMaterial);
    }


    // method to parst the API response and creates Quiz and QuizQuestion objects
    public Quiz processApiResponse(String apiResponse, String quizTitle) throws JSONException {
//        JSONObject jsonResponse = new JSONObject(apiResponse); // converts raw JSON response string into a JSON object
//        String generatedText = jsonResponse.getJSONArray("generations").getJSONObject(0).getString("text"); // extracts the generated quiz test from teh JSON response

        String[] lines = apiResponse.split("\n"); // splits the generated text into individual lines
        List<QuizQuestion> questions = new ArrayList<>();  // Creates a list to store each QuizQuestion object after extracting them from the text

        for (int i = 0; i < lines.length; i += 6) {        // processes 5 lines at a time since each question is expected to occupy 5 lines
            if (i + 5 < lines.length) {                    // ensures there are at least 5 lines remaining to form a full question with answers
                String question = lines[i].substring(3);  // Remove "Q: " prefix
                List<String> answers = new ArrayList<>();
                answers.add(lines[i + 1].substring(3));  // Remove "0: " prefix
                answers.add(lines[i + 2].substring(3));  // Remove "1: " prefix
                answers.add(lines[i + 3].substring(3));  // Remove "2: " prefix
                answers.add(lines[i + 4].substring(3));  // Remove "3: " prefix

                // Extract the correct answer number from the last line in the block
                String correctAnswerLine = lines[i + 5];
                int correctAnswerIndex = Integer.parseInt(correctAnswerLine.replace("Answer: ", "").trim()); // extracts the integer value from the answer and converts it to an integer

                QuizQuestion Q = new QuizQuestion(question, answers, correctAnswerIndex);
                questions.add(Q);
            }
        }

        return new Quiz(quizTitle, questions);
    }

    // convert the Quiz object to JSON
    public JSONObject quizToJson(Quiz quiz) {
        JSONObject quizJson = new JSONObject();
        quizJson.put("Quiz Name", quiz.getName());

        JSONArray questionsArray = new JSONArray();
        for (QuizQuestion question : quiz.getQuestions()) {
            JSONObject questionJson = new JSONObject();
            questionJson.put("question", question.getQuestion());
            questionJson.put("answers", new JSONArray(question.getAnswers()));
            questionJson.put("correctAnswer", question.getCorrectAnswer());
            questionsArray.put(questionJson);
        }

        quizJson.put("Quiz Questions", questionsArray);
        return quizJson;
    }

    // combines the above methods to generate a quiz and convert it to JSON
    public JSONObject generateQuizJson(String courseMaterial, String quizTitle, int numQuestions, String difficulty, int maxTokens) throws IOException, JSONException, ParseException {
        String prompt = createPrompt(courseMaterial, quizTitle, numQuestions, difficulty);
        Quiz quiz = generateQuestions(prompt, maxTokens, quizTitle);
        return quizToJson(quiz);
    }
}
