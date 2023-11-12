package co.com.devsu.movements.infrastructure.acl.dtos.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteAccountRequest {
    private String clientId;
    private String numberAccount;
}
