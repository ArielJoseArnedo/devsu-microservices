package co.com.devsu.clients.infrastructure.messaging.listeners;

import co.com.devsu.clients.infrastructure.events.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Listener {

    String getName();

    Flux<Event> process(JsonNode message);

    default <T extends Message> Mono<T> transform(ObjectMapper objectMapper, JsonNode jsonNode, Class<T> messageClas) {
        return Mono.fromCallable(() -> Try.of(() -> objectMapper.readValue(jsonNode.toPrettyString(), messageClas))
          .getOrElseThrow(() -> new DeserializationEventImpossible("Event deserialization was not possible"))
        );
    }
}
