package use_case.CohereClient;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.*;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;

public class CohereAPI {

        /*
        Cohere cohere = Cohere.builder().clientName("client").build();

        ChatResponse response =
            cohere.v2().chat(
                V2ChatRequest.builder()
                    .model("command-r-plus-08-2024")
                    .messages(List.of(
                        ChatMessageV2.user(
                            UserMessage.builder()
                                .content(UserMessageContent.of("Generate a JSON of .... Who discovered" + " gravity?")).build())))
                    .response_format(){"type": "json_object"})
            .build());
        System.out.println(response);
        */

        /*
        // Build the chat request
        V2ChatRequest request = V2ChatRequest.builder()
                .model("command-r-plus-08-2024")
                .messages(List.of(
                        ChatMessageV2.user(
                                UserMessage.builder()
                                        .content("Generate a JSON of .... Who discovered gravity?")
                                        .build()
                        )
                ))
                .responseFormat(ResponseFormat.text().type("json_object").build())
                .build();

        // Send the chat request and get the response
        ChatResponse response = cohere.v2().chat(request);
        */

        private final Cohere cohere;

        /*
        {
          "Quiz Name": "<quiz name>",
              "Quiz Questions": [
                {
                  "question": "<question 1>",
                  "answers": ["<answer1>", "<answer2>", "..."],
                  "correctAnswer": "<correctAnswer>"
                },
                ...
                {
                  "question": "<question n>",
                  "answers": ["<answer1>", "<answer2>", "..."],
                  "correctAnswer": "<correctAnswer>"
                }
              ]
        }

        // inputs:
        > Quiz Name
        > difficulty
        > number of quiz questions (n)
        > plaintext of course material extracted from PDF


        {
          "title": "Quiz",
          "type": "object",
          "properties": {
            "Quiz Name": {
              "type": "string"
            },
            "Quiz Questions": {
              "type": "array",
              "items": {
                "type": "object",
                "properties": {
                  "question": {
                    "type": "string"
                  },
                  "answers": {
                    "type": "array",
                    "items": {
                      "type": "string"
                    }
                  },
                  "correctIndex": {
                    "type": "integer"
                  }
                },
                "required": ["question", "answers", "correctIndex"]
              }
            }
          },
          "required": ["Quiz Name", "Quiz Questions"]
        }

         */
        // constructor for CohereAPI
        public CohereAPI(String apiKey) {
            this.cohere = Cohere.builder()
                    .token(apiKey)
                    .clientName("quiz-client")
                    .build();
        }

        // generates prompt based on input
        public String createPrompt(String courseMaterial, String quizTitle, int numQuestions, String difficulty) {
            return String.format("Generate JSON %s quiz titled '%s' with %d multiple-choice questions based on the " +
                    "following course material. Each question should have 4 options numbered 1 - 4 with the" +
                    "correct option stored in the answer \n%s", difficulty, quizTitle, numQuestions, courseMaterial);
        }

        // generates JSON format to follow
        public Map<String, Object> createQuizSchema() {
            String jsonSchema = "{\n" +
                    "  \"title\": \"Quiz\",\n" +
                    "  \"type\": \"object\",\n" +
                    "  \"properties\": {\n" +
                    "    \"Quiz Name\": {\n" +
                    "      \"type\": \"string\"\n" +
                    "    },\n" +
                    "    \"Quiz Questions\": {\n" +
                    "      \"type\": \"array\",\n" +
                    "      \"items\": {\n" +
                    "        \"type\": \"object\",\n" +
                    "        \"properties\": {\n" +
                    "          \"question\": {\n" +
                    "            \"type\": \"string\"\n" +
                    "          },\n" +
                    "          \"answers\": {\n" +
                    "            \"type\": \"array\",\n" +
                    "            \"items\": {\n" +
                    "              \"type\": \"string\"\n" +
                    "            }\n" +
                    "          },\n" +
                    "          \"correctIndex\": {\n" +
                    "            \"type\": \"integer\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"required\": [\"question\", \"answers\", \"correctIndex\"]\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"required\": [\"Quiz Name\", \"Quiz Questions\"]\n" +
                    "}";

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            return (gson.fromJson(jsonSchema, type));

            /*
            Map<String, Object> schema = new HashMap<>();
            schema.put("type", "object");

            Map<String, Object> properties = new HashMap<>();
            properties.put("response", Map.of("type", "string"));

            schema.put("properties", properties);
            schema.put("required", new String[]{"response"});
            */
        }

        // makes the call to CohereAPI and returns a String in JSON format
        public String callAPI(String prompt) throws IOException, JSONException {
            Map<String, Object> schema = createQuizSchema();
            JsonResponseFormat jsonFormat = JsonResponseFormat.builder().schema(schema).build();

            ChatRequest request = ChatRequest.builder()
                    .message(prompt)
                    .responseFormat(ResponseFormat.jsonObject(jsonFormat))
                    .build();

            NonStreamedChatResponse response = cohere.chat(request);

            return response.getText();
        }


    /*
    private final Cohere cohere;

    public CohereAPI(String apiKey) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        this.cohere = Cohere.builder()
                .token(apiKey)
                .client(client)  // Use .client() instead of .httpHandler()
                .build();
    }

    public String generateQuizJson(String prompt) {
        Map<String, Object> schema = createSchema();
        JsonResponseFormat jsonFormat = JsonResponseFormat.builder().schema(schema).build();

        ChatRequest request = ChatRequest.builder()
                .message(prompt)
                .responseFormat(ResponseFormat.jsonObject(jsonFormat))
                .build();

        NonStreamedChatResponse response = cohere.chat(request);
        return response.toString();
    }

    private Map<String, Object> createSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");

        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> quizName = new HashMap<>();
        quizName.put("type", "string");
        properties.put("Quiz Name", quizName);

        Map<String, Object> quizQuestions = new HashMap<>();
        quizQuestions.put("type", "array");
        Map<String, Object> items = new HashMap<>();
        items.put("type", "object");
        Map<String, Object> questionProperties = new HashMap<>();
        questionProperties.put("question", Map.of("type", "string"));
        questionProperties.put("answers", Map.of("type", "array", "items", Map.of("type", "string")));
        questionProperties.put("correctAnswer", Map.of("type", "string"));
        items.put("properties", questionProperties);
        items.put("required", new String[]{"question", "answers", "correctAnswer"});
        quizQuestions.put("items", items);
        properties.put("Quiz Questions", quizQuestions);

        schema.put("properties", properties);
        schema.put("required", new String[]{"Quiz Name", "Quiz Questions"});

        return schema;
        */
}

