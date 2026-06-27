package com.monitor;

public record LinuxService(
    String name,
    String status,
    String description
) {}