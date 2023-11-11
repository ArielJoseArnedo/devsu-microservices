package co.com.devsu.clients.infrastructure.controllers;

import co.com.devsu.clients.domain.errors.ClientAlreadyRegisted;
import co.com.devsu.clients.domain.errors.ClientNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientNotFound.class)
    protected ProblemDetail handleClientNotFound(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setDetail("It was not possible to find the client with the identification provided");
        return problemDetail;
    }

    @ExceptionHandler(ClientAlreadyRegisted.class)
    protected ProblemDetail handleClientAlreadyRegisted(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setDetail("The user with the identification provided is already registered in the system");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    protected ProblemDetail handleUnknowException(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Unexpected error has occurred");
        problemDetail.setDetail("Unexpected error has occurred, please contact support");

        log.warn("Unexpected error has occurred: {}", problemDetail, ex);
        return problemDetail;
    }
}
