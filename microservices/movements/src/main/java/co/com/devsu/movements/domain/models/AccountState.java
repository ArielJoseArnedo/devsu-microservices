package co.com.devsu.movements.domain.models;

import io.vavr.collection.List;

public enum AccountState {
    ACTIVE,
    INACTIVE,
    UNKNOWN;

    public static AccountState get(String name) {
        return List.of(AccountState.values())
          .find(clientState -> clientState.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }
}
