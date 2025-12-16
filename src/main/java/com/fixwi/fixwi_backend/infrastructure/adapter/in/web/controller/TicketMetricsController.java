package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.dto.TicketMetricsDTO;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.GetMetricsTicketUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class TicketMetricsController {
    private final GetMetricsTicketUseCase getMetricsTicketUseCase;

    public TicketMetricsController(GetMetricsTicketUseCase getMetricsTicketUseCase) {
        this.getMetricsTicketUseCase = getMetricsTicketUseCase;
    }

    w@PreAuthorize("hasAnyAuthority('ADMIN', 'TI')")
    @GetMapping()
    public ResponseEntity<TicketMetricsDTO> getGeneralMetrics() {
        TicketMetricsDTO metrics = getMetricsTicketUseCase.getGeneralMetrics();
        return ResponseEntity.ok(metrics);
    }
}
