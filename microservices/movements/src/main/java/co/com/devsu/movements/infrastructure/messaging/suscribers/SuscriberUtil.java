package co.com.devsu.movements.infrastructure.messaging.suscribers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

interface SuscriberUtil {

    default Try<JsonNode> getJsonNode(Object message, ObjectMapper objectMapper) {
        return Try.of(() -> objectMapper.readTree(message.toString()));
    }

    default Try<String> getEventName(JsonNode messageJson, String path) {
        return Try.of(() -> messageJson.path(path).asText());
    }

    default Try<JsonNode> getMessage(JsonNode messageJson, String path) {
        return Try.of(() -> messageJson.path(path));
    }
}
