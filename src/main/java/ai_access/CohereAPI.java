package ai_access;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class CohereAPI implements AIAccessInterface {
    private final Cohere cohere;

    public CohereAPI() {
        this.cohere = Cohere.builder().token("qmuC2WGV8FT5vbujGoQW7CV9W6p0ud0JyxTf7epa").clientName("quiz-client").build();
    }

    // generates prompt based on input
    private String createPrompt(String courseMaterial, String quizTitle, int numQuestions, String difficulty) {
        return String.format("Generate JSON %s quiz titled '%s' with %d multiple-choice questions based on the " +
                "following course material. Each question should have 4 options numbered 1 - 4 with the" +
                "correct option stored in the answer \n%s", difficulty, quizTitle, numQuestions, courseMaterial);
    }

    // generates JSON format to follow
    private Map<String, Object> createQuizSchema() {
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
    }

    // makes the call to CohereAPI and returns a String in JSON format
    private String callAPI(String prompt) throws IOException, JSONException {
        Map<String, Object> schema = createQuizSchema();
        JsonResponseFormat jsonFormat = JsonResponseFormat.builder().schema(schema).build();

        ChatRequest request = ChatRequest.builder()
                .message(prompt)
                .responseFormat(ResponseFormat.jsonObject(jsonFormat))
                .build();

        NonStreamedChatResponse response = cohere.chat(request);
        return response.getText();
    }

    @Override
    public String callAPI(String courseMaterial, String quizName, Integer numQuestions, String difficulty) throws IOException {
       String prompt = createPrompt(courseMaterial, quizName, numQuestions, difficulty);
       return callAPI(prompt);
    }
}
