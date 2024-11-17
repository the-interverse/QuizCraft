package use_case.CohereClient;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.cohere.api.types.ChatFinishReason;

import java.io.IOException;

public class ChatFinishReasonDeserializer extends JsonDeserializer<ChatFinishReason> {
    @Override
    public ChatFinishReason deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString().toLowerCase();
        for (ChatFinishReason reason : ChatFinishReason.values()) {
            if (reason.name().toLowerCase().equals(value)) {
                return reason;
            }
        }
        return null; // or throw an exception if you prefer
    }
}