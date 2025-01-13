package com.example.edra_labs_machine_coding.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ApiKey {

    private final String keyId;
    private final LocalDateTime createdAt;
    private LocalDateTime lastKeepAlive;
    private boolean isBlocked;
    private LocalDateTime blockedAt;

    public ApiKey() {
        this.keyId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.lastKeepAlive = createdAt;
        this.isBlocked = false;
    }

    public String getKeyId() {
        return keyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastKeepAlive() {
        return lastKeepAlive;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setLastKeepAlive(LocalDateTime lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
        blockedAt = blocked ? LocalDateTime.now() : null;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiKey apiKey = (ApiKey) o;
        return isBlocked == apiKey.isBlocked && Objects.equals(keyId, apiKey.keyId) && Objects.equals(createdAt, apiKey.createdAt) && Objects.equals(lastKeepAlive, apiKey.lastKeepAlive) && Objects.equals(blockedAt, apiKey.blockedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyId, createdAt, lastKeepAlive, isBlocked, blockedAt);
    }
}
