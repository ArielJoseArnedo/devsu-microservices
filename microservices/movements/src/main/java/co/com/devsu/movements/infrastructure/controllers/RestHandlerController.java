package co.com.devsu.movements.infrastructure.controllers;

import co.com.devsu.movements.domain.errors.AccountNotFound;
import co.com.devsu.movements.domain.errors.BalanceNotAvailable;
import co.com.devsu.movements.domain.errors.MovementTypeNotSupported;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFound.class)
    protected ProblemDetail handleAccountNotFound(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setDetail("It was not possible to find the account with data provided");
        return problemDetail;
    }

    @ExceptionHandler(BalanceNotAvailable.class)
    protected ProblemDetail handleBalanceNotAvailable(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setDetail("Insufficient balance to make this transaction");
        return problemDetail;
    }

    @ExceptionHandler(MovementTypeNotSupported.class)
    protected ProblemDetail handleMovementTypeNotSupported(Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setDetail("Unsupported banking movement");
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
