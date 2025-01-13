package uk.tw.energy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.tw.energy.service.EnergyUsageService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/energy-usage")
public class EnergyUsageController {

    private final EnergyUsageService energyUsageService;

    public EnergyUsageController(EnergyUsageService energyUsageService) {
        this.energyUsageService = energyUsageService;
    }

    @GetMapping("/weekly2/{smartMeterId}")
    public ResponseEntity getWeeklyusage2(@PathVariable String smartMeterId) {

        Instant endDate = Instant.now();
        Instant startDate = endDate.minus(7, ChronoUnit.DAYS);

        return calculateUsageCost(smartMeterId, startDate, endDate)
    }

    @GetMapping("/weekly/{smartMeterId}")
    public ResponseEntity<?> getWeeklyUsageCost(@PathVariable String smartMeterId) {
        Instant endDate = Instant.now();
        Instant startDate = endDate.minus(7, ChronoUnit.DAYS);
        return calculateUsageCost(smartMeterId, startDate, endDate);
    }

    @GetMapping("/monthly/{smartMeterId}")
    public ResponseEntity<?> getMonthlyUsageCost(@PathVariable String smartMeterId) {
        Instant endDate = Instant.now();
        Instant startDate = endDate.minus(30, ChronoUnit.DAYS);
        return calculateUsageCost(smartMeterId, startDate, endDate);
    }

    @GetMapping("/yearly/{smartMeterId}")
    public ResponseEntity<?> getYearlyUsageCost(@PathVariable String smartMeterId) {
        Instant endDate = Instant.now();
        Instant startDate = endDate.minus(365, ChronoUnit.DAYS);
        return calculateUsageCost(smartMeterId, startDate, endDate);
    }

    @GetMapping("/custom/{smartMeterId}")
    public ResponseEntity<?> getCustomUsageCost(
            @PathVariable String smartMeterId,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate) {
        return calculateUsageCost(smartMeterId, startDate, endDate);
    }

    private ResponseEntity<BigDecimal> calculateUsageCost(String smartMeterId, Instant startDate, Instant endDate) {
        try {
            BigDecimal cost = energyUsageService.calculateUsageCost(smartMeterId, startDate, endDate);
            return ResponseEntity.ok(cost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ResponseEntity<?> calculate(String smartMeterId, Instant startDate, Instant endDate) {

        try {
            BigDecimal cost = energyUsageService.calculateUsageCost2(smartMeterId, startDate, endDate);
            return ResponseEntity.ok(cost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
