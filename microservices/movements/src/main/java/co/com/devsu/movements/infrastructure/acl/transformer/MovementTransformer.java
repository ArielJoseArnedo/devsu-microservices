package co.com.devsu.movements.infrastructure.acl.transformer;

import co.com.devsu.movements.domain.models.Movement;
import co.com.devsu.movements.domain.models.MovementType;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterMovementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovementTransformer {

    default Movement toDomain(RegisterMovementRequest registerMovementRequest) {
        return Movement.builder()
          .movementType(MovementType.get(registerMovementRequest.getMovementType()))
          .amount(registerMovementRequest.getAmount())
          .registrationDate(ZonedDateTime.now())
          .build();
    }
}
