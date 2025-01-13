package com.example.edra_labs_machine_coding.service;

import com.example.edra_labs_machine_coding.model.ApiKey;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class ApiKeyService {

    private final Map<String, ApiKey> keys = new ConcurrentHashMap<>();
    private final Set<String> availableKeys = Collections.synchronizedSet(new HashSet<>());
    private final PriorityBlockingQueue<ApiKey> expirationQueue
            = new PriorityBlockingQueue<>(10, Comparator.comparing(ApiKey::getLastKeepAlive));

    private final Map<String, LocalDateTime> blockedKeys = new ConcurrentHashMap<>();
    public ApiKey createKey() {
        ApiKey apiKey = new ApiKey();
        keys.put(apiKey.getKeyId(), apiKey);
        availableKeys.add(apiKey.getKeyId());
        expirationQueue.offer(apiKey);
        return apiKey;
    }

    public Optional<ApiKey> getAvailableKey() {
//        return keys.values().stream()
//                .filter(apiKey -> !apiKey.isBlocked() && !isExpired(apiKey))
//                .findAny()
//                .map(apiKey ->  {
//                        apiKey.setBlocked(true);
//                        return apiKey;
//                        });


        synchronized (availableKeys) {
            Iterator<String> iterator = availableKeys.iterator();
            if(iterator.hasNext()) {
                String keyId = iterator.next();
                ApiKey apiKey = keys.get(keyId);
                apiKey.setBlocked(true);
                blockedKeys.put(apiKey.getKeyId(), LocalDateTime.now());
                iterator.remove();;
                return Optional.of(apiKey);
            }

            // time complexity - O(1)
            return Optional.empty();
        }
    }

    public Optional<ApiKey> getKeyInfo(String id) {

        ApiKey apiKey = keys.get(id);
        System.out.println(apiKey.getLastKeepAlive());
        return Optional.ofNullable(keys.get(id));
    }

    public boolean deleteKey(String id) {
        ApiKey apiKey = keys.remove(id);
        if(apiKey != null) {
            availableKeys.remove(id);
            expirationQueue.remove(apiKey);
            blockedKeys.remove(id);
            return true;
        }
        return false;
    }

    public boolean unblockKey(String id) {
//        ApiKey apiKey = keys.get(id);
//
//        if(apiKey != null && apiKey.isBlocked()) {
//            apiKey.setBlocked(false);
//            return true;
//        }
//
//        return false;

        if(blockedKeys.containsKey(id)) {
            blockedKeys.remove(id);
            availableKeys.add(id);
            keys.get(id).setBlocked(false);
            return true;
        }

        // time complexity - O(1)
        return false;
    }

    public boolean keepAlive(String id) {
        ApiKey apiKey = keys.get(id);

        if(apiKey != null) {
            apiKey.setLastKeepAlive(LocalDateTime.now());
            expirationQueue.remove(apiKey);
            expirationQueue.offer(apiKey);
            return true;
        }
        // time complexity - O(logn)
        return false;
    }

    public void expireKeys() {
        LocalDateTime now = LocalDateTime.now();

        while (!expirationQueue.isEmpty() && Duration.between(expirationQueue.peek().getBlockedAt(), now).toMinutes() >= 5) {
            ApiKey expiredKey = expirationQueue.poll();
            if(expiredKey != null) {
                keys.remove(expiredKey.getKeyId());
                availableKeys.remove(expiredKey.getKeyId());
                blockedKeys.remove(expiredKey.getKeyId());
            }
        }
    }

    public void releaseBlockedKeys() {
        LocalDateTime now = LocalDateTime.now();
//        keys.values().stream()
//                .filter(ApiKey::isBlocked)
//                .filter(apiKey -> Duration.between(apiKey.getBlockedAt(), now).getSeconds() > 60)
//                .forEach(apiKey -> apiKey.setBlocked(false));

        blockedKeys.forEach((keyId, blockedAt) -> {
            if(Duration.between(blockedAt, now).getSeconds() > 60) {
                blockedKeys.remove(keyId);
                availableKeys.add(keyId);
                ApiKey apiKey = keys.get(keyId);
                if(apiKey != null) {
                    apiKey.setBlocked(false);
                }
            }
        });
    }

}
