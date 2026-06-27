package com.monitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<MetricsEntity, Long> {
    // Spring generates implementations for database actions automatically
}