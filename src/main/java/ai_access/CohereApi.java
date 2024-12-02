package ai_access;

import java.util.Map;

import org.json.JSONException;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.*;

public class CohereApi extends AbstractAiPrompter {
    private final Cohere cohere;

    public CohereApi() {
        this.cohere = Cohere.builder().token("qmuC2WGV8FT5vbujGoQW7CV9W6p0ud0JyxTf7epa").clientName("quiz-client")
            .build();
    }

    @Override
    public String callApi(String prompt, Map<String, Object> schema) throws JSONException {
        final JsonResponseFormat jsonFormat = JsonResponseFormat.builder().schema(schema).build();

        final ChatRequest request = ChatRequest.builder()
            .message(prompt)
            .responseFormat(ResponseFormat.jsonObject(jsonFormat))
            .build();

        final NonStreamedChatResponse response = cohere.chat(request);
        return response.getText();
    }
}
