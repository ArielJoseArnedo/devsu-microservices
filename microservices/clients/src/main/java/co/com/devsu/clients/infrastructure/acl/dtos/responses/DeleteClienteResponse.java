package co.com.devsu.clients.infrastructure.acl.dtos.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteClienteResponse {
    private final String clientId;
}
