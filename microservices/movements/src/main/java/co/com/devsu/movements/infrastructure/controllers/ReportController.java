package co.com.devsu.movements.infrastructure.controllers;

import co.com.devsu.movements.infrastructure.acl.services.ReportService;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/reportes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<JsonNode> getReport(
      @RequestParam("id") String clientId,
      @RequestParam(name = "account", required = false) String numberAccount
      ) {
        return reportService.getReport(clientId, Option.of(numberAccount));
    }
}
