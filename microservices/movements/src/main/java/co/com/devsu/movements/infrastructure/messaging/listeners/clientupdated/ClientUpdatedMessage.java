package co.com.devsu.movements.infrastructure.messaging.listeners.clientupdated;

import co.com.devsu.movements.infrastructure.messaging.listeners.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientUpdatedMessage implements Message {
    private String clientId;
    private String name;
}
