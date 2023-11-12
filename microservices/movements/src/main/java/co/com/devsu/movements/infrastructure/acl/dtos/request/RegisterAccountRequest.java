package co.com.devsu.movements.infrastructure.acl.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterAccountRequest {
    private String clientId;
    private String numberAccount;
    private String accountType;
    private double openingBalance;
}
