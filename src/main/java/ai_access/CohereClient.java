package ai_access;

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
            questionJson.put("correctAnswer", question.getCorrectIndex());
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
