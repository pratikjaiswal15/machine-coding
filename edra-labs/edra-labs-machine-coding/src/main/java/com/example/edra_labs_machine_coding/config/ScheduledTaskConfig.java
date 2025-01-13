package com.example.edra_labs_machine_coding.config;

import com.example.edra_labs_machine_coding.service.ApiKeyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskConfig {

    private final ApiKeyService keyService;

    public ScheduledTaskConfig(ApiKeyService keyService) {
        this.keyService = keyService;
    }

    @Scheduled(fixedRate = 30000)
    public void expireKeys() {
        keyService.expireKeys();
    }

    @Scheduled(fixedRate = 30000)
    public void releaseBlockedKeys() {
        keyService.releaseBlockedKeys();
    }
}
