package co.com.devsu.clients.domain.models;

import io.vavr.collection.List;

public enum ClientState {
    ACTIVE,
    INACTIVE,
    UNKNOWN;

    public static ClientState get(String name) {
        return List.of(ClientState.values())
          .find(clientState -> clientState.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }
}
