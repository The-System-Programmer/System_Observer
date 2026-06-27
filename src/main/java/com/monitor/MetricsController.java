package com.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    // Endpoint for current instantaneous metrics
    @GetMapping("/metrics")
    public SystemMetrics getMetrics() {
        return metricsService.getSystemMetrics();
    }

    // Endpoint for live background daemons state
    @GetMapping("/services")
    public List<LinuxService> getServices() {
        return metricsService.getTrackedServices();
    }

    // Endpoint for reading all entries from the persistent file database
    @GetMapping("/history")
    public List<MetricsEntity> getHistory() {
        return metricsService.getAllHistoricalRecords();
    }
}