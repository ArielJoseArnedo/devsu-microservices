package co.com.devsu.movements.infrastructure.acl.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MovementReportReponse implements ReportResponse{
    private final String date;
    private final String client;
    private final String numberAccount;
    private final String accountType;
    private final double openingBalance;
    private final String state;
    private final double movement;
    private final double balance;
}
