package co.com.devsu.clients.infrastructure.acl.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetClientResonse {
    private final String clientId;
    private final String name;
    private final String identification;
    private final String state;
    private final String gender;
    private final int age;
    private final String address;
    private final String phone;
}
