package com.monitor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    // Arch Linux and Fedora standardized service names
    private final String[] trackedDaemons = {
        "NetworkManager", "sshd", "docker", "httpd", "vsftpd", "dnsmasq"
    };

    // Background job: Fires automatically every 10 seconds (10000 ms)
    @Scheduled(fixedRate = 10000)
    public void captureAndSaveToDatabase() {
        SystemMetrics currentLiveMetrics = getSystemMetrics();
        MetricsEntity DBRecord = new MetricsEntity(currentLiveMetrics);
        metricsRepository.save(DBRecord);
        System.out.println("💾 [Database Log] Saved hardware snapshot at: " + DBRecord.getTimestamp());
    }

    public List<MetricsEntity> getAllHistoricalRecords() {
        return metricsRepository.findAll();
    }

    public SystemMetrics getSystemMetrics() {
        String uptime = executeCommand("uptime -p");
        String cpuLoad = executeCommand("cat /proc/loadavg | awk '{print $1}'");
        
        String memRaw = executeCommand("free -m | awk 'NR==2{print $2 \",\" $3}'");
        String[] memData = memRaw.split(",");
        String memoryTotal = memData.length > 0 ? memData[0] + " MB" : "N/A";
        String memoryUsed = memData.length > 1 ? memData[1] + " MB" : "N/A";

        String diskRaw = executeCommand("df -h / | awk 'NR==2{print $2 \",\" $3}'");
        String[] diskData = diskRaw.split(",");
        String diskTotal = diskData.length > 0 ? diskData[0] : "N/A";
        String diskUsed = diskData.length > 1 ? diskData[1] : "N/A";

        String localIp = executeCommand("ip -4 addr show scope global | grep inet | awk '{print $2}' | cut -d/ -f1 | head -n1");
        if (localIp.isEmpty()) { localIp = "127.0.0.1"; }

        String netRaw = executeCommand("cat /proc/net/dev | awk 'NR>2 {rx+=$2; tx+=$10} END {printf \"%.1f MB,%.1f MB\", rx/1024/1024, tx/1024/1024}'");
        String[] netData = netRaw.split(",");
        String networkRx = netData.length > 0 ? netData[0] : "N/A";
        String networkTx = netData.length > 1 ? netData[1] : "N/A";

        return new SystemMetrics(uptime, cpuLoad, memoryUsed, memoryTotal, diskUsed, diskTotal, localIp, networkRx, networkTx);
    }

    public List<LinuxService> getTrackedServices() {
        List<LinuxService> servicesList = new ArrayList<>();
        for (String daemon : trackedDaemons) {
            String status = executeCommand("systemctl is-active " + daemon);
            String description = executeCommand("systemctl show " + daemon + " --property=Description | cut -d= -f2");
            if (description.isEmpty()) { 
                description = "Service script configuration entry not found/installed"; 
            }
            servicesList.add(new LinuxService(daemon, status, description));
        }
        return servicesList;
    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();
        } catch (Exception e) {
            return "";
        }
        return output.toString().trim();
    }
}