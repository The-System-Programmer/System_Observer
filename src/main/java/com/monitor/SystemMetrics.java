package com.monitor;

public record SystemMetrics(
    String uptime,
    String cpuLoad,
    String memoryUsed,
    String memoryTotal,
    String diskUsed,
    String diskTotal,
    String localIp,
    String networkRx,
    String networkTx
) {}