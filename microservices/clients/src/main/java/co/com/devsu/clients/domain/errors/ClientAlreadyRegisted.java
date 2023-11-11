package co.com.devsu.clients.domain.errors;

import co.com.devsu.clients.domain.ports.AppError;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


public class ClientAlreadyRegisted extends Exception {
    public ClientAlreadyRegisted(String message) {
        super(message);
    }
}
