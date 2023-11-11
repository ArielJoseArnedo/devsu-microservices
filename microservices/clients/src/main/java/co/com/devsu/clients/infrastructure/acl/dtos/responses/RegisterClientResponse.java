package co.com.devsu.clients.infrastructure.acl.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RegisterClientResponse {
    private final String clientId;
    protected final String name;
}
