package co.com.devsu.clients.domain.models;

import io.vavr.collection.List;

public enum Gender {
    MALE,
    FEMALE,
    BINARY,
    IDK,
    UNKNOWN;

    public static Gender get(String name) {
        return List.of(Gender.values())
          .find(gender -> gender.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }
}
