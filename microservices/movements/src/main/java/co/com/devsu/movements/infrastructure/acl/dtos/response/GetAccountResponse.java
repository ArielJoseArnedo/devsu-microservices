package co.com.devsu.movements.infrastructure.acl.dtos.response;

import co.com.devsu.movements.domain.models.AccountState;
import co.com.devsu.movements.domain.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAccountResponse {
    private final String clientId;
    private final String clientName;
    private final String numberAccount;
    private final String accountType;
    private final double generalBalance;
    private final String state;
}
