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
    private final MovementType movementType;
    private final double amount;
    private final double balance;

    public Movement updateBalance(double newBalance) {
        return Movement.builder()
          .registrationDate(registrationDate)
          .movementType(movementType)
          .amount(amount)
          .balance(newBalance)
          .build();
    }
}
