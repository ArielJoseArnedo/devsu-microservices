package co.com.devsu.movements.infrastructure.acl.dtos.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterMovementResponse {
    private final double amount;
}
