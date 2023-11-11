package co.com.devsu.clients.infrastructure.acl.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClientRequest {

    private String clientId;
    private String password;
    private String state;
    protected String name;
    protected String identification;
    protected String gender;
    protected int age;
    protected String address;
    protected String phone;
}
