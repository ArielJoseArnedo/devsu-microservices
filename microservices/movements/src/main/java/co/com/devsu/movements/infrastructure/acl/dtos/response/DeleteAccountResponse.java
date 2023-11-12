package co.com.devsu.movements.infrastructure.acl.dtos.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DeleteAccountResponse {
    private final String clientId;
    private final String numberAccount;
}
