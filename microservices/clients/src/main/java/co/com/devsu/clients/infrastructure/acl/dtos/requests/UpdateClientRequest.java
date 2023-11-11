package co.com.devsu.clients.infrastructure.acl.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateClientRequest {

    private String clientId;
    private String password;
    private String name;
    private String state;
    private String gender;
    private int age;
    private String address;
    private String phone;
}
