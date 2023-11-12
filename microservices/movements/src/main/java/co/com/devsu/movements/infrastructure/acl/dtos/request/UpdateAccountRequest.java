package co.com.devsu.movements.infrastructure.acl.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAccountRequest {
    private String clientId;
    private String numberAccount;
    private String accountType;
    private String state;
}
