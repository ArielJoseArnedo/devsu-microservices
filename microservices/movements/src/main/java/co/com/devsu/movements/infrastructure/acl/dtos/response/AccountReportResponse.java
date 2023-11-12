package co.com.devsu.movements.infrastructure.acl.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class AccountReportResponse implements ReportResponse {
    private final String numberAccount;
    private final String accountType;
    private final double balance;
    private final String state;
    private final String client;
}
