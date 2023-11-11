package co.com.devsu.clients.infrastructure.acl.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientRequest {
    private String clientId;
}
