package com.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Required to run background @Scheduled timer threads
public class LinuxMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinuxMonitorApplication.class, args);
    }
}