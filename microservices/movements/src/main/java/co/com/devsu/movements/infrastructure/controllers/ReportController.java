package co.com.devsu.movements.infrastructure.controllers;

import co.com.devsu.movements.infrastructure.acl.services.ReportService;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<JsonNode> getReport(
      @RequestParam("id") String clientId,
      @RequestParam(name = "account", required = false)  String numberAccount,
      @RequestParam(name = "dateStart", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateStart,
      @RequestParam(name = "dateEnd", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateEnd
      ) {

        final Option<String> numberAccountOpt = Option.of(numberAccount);
        final Option<LocalDate> dateStartOpt = Option.of(dateStart);
        final Option<LocalDate> dateEndOpt = Option.of(dateEnd);
        return reportService.getReport(clientId, numberAccountOpt, dateStartOpt, dateEndOpt);
    }
}
