package com.example.edra_labs_machine_coding.controller;

import com.example.edra_labs_machine_coding.model.ApiKey;
import com.example.edra_labs_machine_coding.service.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/keys")
public class ApiKeyController {

    private final ApiKeyService keyService;


    public ApiKeyController(ApiKeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping()
    public ResponseEntity<Void> createKey() {
        keyService.createKey();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getAvailableKey() {
        Optional<ApiKey> availableKey = keyService.getAvailableKey();
        return availableKey
                .map(apiKey -> ResponseEntity.ok(Map.of("keyId", apiKey.getKeyId())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getKeyInfo(@PathVariable String id) {
        Optional<ApiKey> optionalApiKey =  keyService.getKeyInfo(id);

        if(optionalApiKey.isPresent()) {
            System.out.println("Key is present");
            ApiKey apiKey = optionalApiKey.get();
            System.out.println(apiKey);
            Map<String, Object> response = Map.of(
                    "isBlocked", apiKey.isBlocked(),
                    "blockedAt", apiKey.getBlockedAt(),
                    "createdAt", apiKey.getCreatedAt());
            return ResponseEntity.ok(response);
        } else {
            System.out.println("not present");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKey(@PathVariable String id) {
        if(keyService.deleteKey(id)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> unblockKey(@PathVariable String id) {
        if(keyService.unblockKey(id)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/keepalive/{id}")

    public ResponseEntity<Void> keepAlive(@PathVariable String id) {
        if(keyService.keepAlive(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
 }
