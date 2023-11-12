package co.com.devsu.movements.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class Movement {
    private final ZonedDateTime registrationDate;
    private final String movementType;
    private final double amount;
}
