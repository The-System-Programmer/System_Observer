package com.monitor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_history")
public class MetricsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String uptime;
    private String cpuLoad;
    private String memoryUsed;
    private String memoryTotal;
    private String diskUsed;
    private String diskTotal;
    private String localIp;
    private String networkRx;
    private String networkTx;

    // Default constructor for JPA reflection
    public MetricsEntity() {}

    // Convenience mapping constructor
    public MetricsEntity(SystemMetrics metrics) {
        this.timestamp = LocalDateTime.now();
        this.uptime = metrics.uptime();
        this.cpuLoad = metrics.cpuLoad();
        this.memoryUsed = metrics.memoryUsed();
        this.memoryTotal = metrics.memoryTotal();
        this.diskUsed = metrics.diskUsed();
        this.diskTotal = metrics.diskTotal();
        this.localIp = metrics.localIp();
        this.networkRx = metrics.networkRx();
        this.networkTx = metrics.networkTx();
    }

    // Getters
    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getUptime() { return uptime; }
    public String getCpuLoad() { return cpuLoad; }
    public String getMemoryUsed() { return memoryUsed; }
    public String getMemoryTotal() { return memoryTotal; }
    public String getDiskUsed() { return diskUsed; }
    public String getDiskTotal() { return diskTotal; }
    public String getLocalIp() { return localIp; }
    public String getNetworkRx() { return networkRx; }
    public String getNetworkTx() { return networkTx; }
}