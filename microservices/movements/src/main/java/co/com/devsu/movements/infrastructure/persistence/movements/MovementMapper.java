package co.com.devsu.movements.infrastructure.persistence.movements;

import co.com.devsu.movements.domain.models.Movement;
import co.com.devsu.movements.domain.models.MovementType;
import co.com.devsu.movements.infrastructure.persistence.accounts.AccountKey;
import co.com.devsu.movements.infrastructure.util.DateUtil;

import java.util.UUID;

public interface MovementMapper {

    default Movement toDomain(MovementRecord movementRecord) {
        return Movement.builder()
          .movementType(MovementType.get(movementRecord.getMovementType()))
          .amount(movementRecord.getAmount())
          .balance(movementRecord.getBalance())
          .registrationDate(DateUtil.fromTimestamp(movementRecord.getRegistrationDate()))
          .build();
    }

    default MovementRecord toRecord(Movement movement, String clientId, String numberAccount) {
        return MovementRecord.builder()
          .movementKey(new MovementKey(new AccountKey(clientId, numberAccount), UUID.randomUUID().toString()))
          .movementType(movement.getMovementType().name())
          .amount(movement.getAmount())
          .balance(movement.getBalance())
          .registrationDate(DateUtil.fromZonedDateTime(movement.getRegistrationDate()))
          .build();
    }
}
