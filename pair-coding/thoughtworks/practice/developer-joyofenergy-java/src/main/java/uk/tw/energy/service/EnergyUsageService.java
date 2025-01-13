package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.utility.CalculateCost;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EnergyUsageService {

    private final MeterReadingService meterReadingService;
    private final AccountService accountService;
    private final List<PricePlan> pricePlans;

    public EnergyUsageService(MeterReadingService meterReadingService, AccountService accountService, List<PricePlan> pricePlans) {
        this.meterReadingService = meterReadingService;
        this.accountService = accountService;
        this.pricePlans = pricePlans;
    }

    public BigDecimal calculateUsageCost2(String smartMeterId, Instant startDate, Instant endDate) {

        String pricePlanName = accountService.getPricePlanIdForSmartMeterId(smartMeterId);
        if(pricePlanName == null) {
            throw new IllegalArgumentException("No price plan associated with this smart meter Id");
        }

        List<ElectricityReading> readings = meterReadingService.getReadings(smartMeterId)
                .orElseThrow(() -> new IllegalArgumentException("No readings found for this smart meter"));

        List<ElectricityReading> filteredReadings = readings.stream()
                .filter(r -> r.time().isAfter(startDate) && r.time().isBefore(endDate))
                .toList();

        if(filteredReadings.isEmpty()) {
            throw new IllegalArgumentException("No reading for specified time range");
        }


        Optional<PricePlan> pricePlan = getPricePlaneByName(pricePlanName, pricePlans);

        if(pricePlan.isEmpty()) {
            throw new IllegalArgumentException("No reading for specified time range");
        }

        return calculateCost2(readings, pricePlan.get());
    }

    private Optional<PricePlan> getPricePlaneByName(String pricePlanName, List<PricePlan> pricePlans) {
        return pricePlans.stream()
                .filter(pricePlan -> pricePlan.getPlanName().equals(pricePlanName))
                .findFirst();
    }
    private BigDecimal calculateCost2(List<ElectricityReading> readings, PricePlan pricePlan) {

        return CalculateCost.calculateCost(readings, pricePlan);
    }

    private BigDecimal calculateAverageReading(List<ElectricityReading> readings, String pricePlanName) {
        BigDecimal sumReadings = readings.stream()
                .map(ElectricityReading::reading)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // return sumReadings.divide(BigDecimal.valueOf(readings.size()), RoundingMode.HALF_UP);


        BigDecimal sumReadings2 = readings.stream()
                .map((ElectricityReading::reading))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumReadings2.divide(BigDecimal.valueOf(readings.size()), RoundingMode.HALF_UP);
    }


    public BigDecimal calculateUsageCost(String smartMeterId, Instant startDate, Instant endDate) {
        // Fetch the associated price plan
        String pricePlanName = accountService.getPricePlanIdForSmartMeterId(smartMeterId);
        if (pricePlanName == null) {
            throw new IllegalArgumentException("No price plan associated with smart meter ID");
        }

        // Fetch electricity readings
        List<ElectricityReading> readings = meterReadingService.getReadings(smartMeterId)
                .orElseThrow(() -> new IllegalArgumentException("No readings found for smart meter ID"));

        // Filter readings for the specified date range
        List<ElectricityReading> filteredReadings = readings.stream()
                .filter(r -> r.time().isAfter(startDate) && r.time().isBefore(endDate))
                .toList();

        if (filteredReadings.isEmpty()) {
            throw new IllegalArgumentException("No readings available for the specified date range.");
        }

        // Calculate the cost
        return calculateCost(filteredReadings, pricePlanName);
    }

    private BigDecimal calculateCost(List<ElectricityReading> readings, String pricePlanName) {
        // Calculate average reading
        BigDecimal averageReading = readings.stream()
                .map(ElectricityReading::reading)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(readings.size()), RoundingMode.HALF_UP);

        // Calculate duration in hours
        BigDecimal durationInHours = BigDecimal.valueOf(Duration.between(
                readings.get(0).time(),
                readings.get(readings.size() - 1).time()
        ).toHours());

        // Calculate energy consumed
        BigDecimal energyConsumed = averageReading.multiply(durationInHours);

        // Fetch unit rate for the price plan
        BigDecimal unitRate = pricePlans.stream()
                .filter(plan -> plan.getPlanName().equals(pricePlanName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Price plan not found"))
                .getUnitRate();

        return energyConsumed.multiply(unitRate);
    }
}
