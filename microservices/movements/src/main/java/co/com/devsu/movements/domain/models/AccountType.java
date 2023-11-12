package co.com.devsu.movements.domain.models;

import io.vavr.collection.List;

public enum AccountType {
    SAVINGS,
    CHECKING,
    UNKNOWN;

    public static AccountType get(String name) {
        return List.of(AccountType.values())
          .find(clientState -> clientState.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }
}
