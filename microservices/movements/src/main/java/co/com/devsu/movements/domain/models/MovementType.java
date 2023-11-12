package co.com.devsu.movements.domain.models;

import io.vavr.collection.List;

import java.util.function.Predicate;

public enum MovementType {
    DEPOSIT,
    WITHDRAWAL,
    UNKNOWN;

    public static MovementType get(String name) {
        return List.of(MovementType.values())
          .find(clientState -> clientState.name().equalsIgnoreCase(name))
          .getOrElse(UNKNOWN);
    }

    public static Predicate<MovementType> evaluate(String value) {
        return movementType -> movementType.name().equalsIgnoreCase(value);
    }
}
