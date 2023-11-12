package co.com.devsu.movements.infrastructure.acl.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterMovementRequest {

    private String clientId;
    private String numberAccount;
    private String movementType;
    private double amount;
}
