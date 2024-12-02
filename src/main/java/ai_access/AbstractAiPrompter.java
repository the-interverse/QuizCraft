package ai_access;

import java.lang.reflect.Type;
import java.util.Map;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class AbstractAiPrompter {
    /**
     * Generates the quiz via AI.
     * @param courseMaterial course material
     * @param quizName name of the quiz
     * @param numQuestions number of questions
     * @param difficulty difficulty of the quiz
     * @return quiz in string format
     */
    public String generateQuiz(String courseMaterial, String quizName, Integer numQuestions, String difficulty) {
        final String prompt = createPrompt(courseMaterial, quizName, numQuestions, difficulty);
        final Map<String, Object> schema = createQuizSchema();
        String result;
        try {
            result = callApi(prompt, schema);
        }
        catch (JSONException exception) {
            result = null;
        }
        return result;
    }

    /**
     * Generate prompt for calling an AI endpoint.
     * @param courseMaterial course material
     * @param quizName name of the quiz
     * @param numQuestions number of questions
     * @param difficulty difficulty of the quiz
     * @return prompt for AI.
     */
    public String createPrompt(String courseMaterial, String quizName, Integer numQuestions, String difficulty) {
        return String.format("Generate JSON %s quiz titled '%s' with %d multiple-choice questions based on the "
            + "following course material. Each question should have 4 options numbered 1 - 4 with the"
            + "correct option stored in the answer \n%s", difficulty, quizName, numQuestions, courseMaterial);
    }

    protected abstract String callApi(String prompt, Map<String, Object> schema) throws JSONException;

    protected Map<String, Object> createQuizSchema() {
        final String jsonSchema = "{\n"
            +
            "  \"title\": \"Quiz\",\n"
            +
            "  \"type\": \"object\",\n"
            +
            "  \"properties\": {\n"
            +
            "    \"Quiz Name\": {\n"
            +
            "      \"type\": \"string\"\n"
            +
            "    },\n"
            +
            "    \"Quiz Questions\": {\n"
            +
            "      \"type\": \"array\",\n"
            +
            "      \"items\": {\n"
            +
            "        \"type\": \"object\",\n"
            +
            "        \"properties\": {\n"
            +
            "          \"question\": {\n"
            +
            "            \"type\": \"string\"\n"
            +
            "          },\n"
            +
            "          \"answers\": {\n"
            +
            "            \"type\": \"array\",\n"
            +
            "            \"items\": {\n"
            +
            "              \"type\": \"string\"\n"
            +
            "            }\n"
            +
            "          },\n"
            +
            "          \"correctIndex\": {\n"
            +
            "            \"type\": \"integer\"\n"
            +
            "          }\n"
            +
            "        },\n"
            +
            "        \"required\": [\"question\", \"answers\", \"correctIndex\"]\n"
            +
            "      }\n"
            +
            "    }\n"
            +
            "  },\n"
            +
            "  \"required\": [\"Quiz Name\", \"Quiz Questions\"]\n"
            +
            "}";
        final Gson gson = new Gson();
        final Type type = new TypeToken<Map<String, Object>>() { }.getType();
        return gson.fromJson(jsonSchema, type);
    }
}
