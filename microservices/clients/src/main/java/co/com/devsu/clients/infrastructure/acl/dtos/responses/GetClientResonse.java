package co.com.devsu.clients.infrastructure.acl.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClientResonse {
    private String clientId;
    private String name;
    private String identification;
    private String state;
    private String gender;
    private int age;
    private String address;
    private String phone;
}
