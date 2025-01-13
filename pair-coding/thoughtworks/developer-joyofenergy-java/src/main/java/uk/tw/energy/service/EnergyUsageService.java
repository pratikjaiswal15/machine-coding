package uk.tw.energy.service;



import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EnergyUsageService {

    private AccountService accountService;
    private MeterReadingService meterReadingService;
    private PricePlanService pricePlanService;

    private final int WEEKLY_HOURS = 168;
    public EnergyUsageService(AccountService accountService, MeterReadingService meterReadingService, PricePlanService pricePlanService) {
        this.accountService = accountService;
        this.meterReadingService = meterReadingService;
        this.pricePlanService = pricePlanService;
    }

    public BigDecimal calculateUsage(String smartMeterId, Instant startDate, Instant endDate) {
        // find the price plan

        String pricePlan = accountService.getPricePlanIdForSmartMeterId(smartMeterId);

        // throw error if no price plan
        if(pricePlan == null) {
            throw new IllegalArgumentException("Price Plane does not exists for this smart meter");
        }


        // get all readings from hashmap Id - smart meter id

        Optional<List<ElectricityReading>> readings = meterReadingService.getReadings(smartMeterId);

        if(readings.isEmpty()) {
            throw new IllegalArgumentException("Readings does not exists for this smart meter");
        }

        // filter reading wrt week
        List<ElectricityReading> filteredReadings = readings.get().stream()
                .filter(reading -> reading.time().isAfter(startDate) && reading.time().isBefore(endDate))
                .toList();

        PricePlan pricePlan = pr
        // calculate cost
        return calculateActualCost(filteredReadings);
        // return BigDecimal.ZERO;
    }

    private PricePlan getPricePlanByName(String pricePlan) {

    }
    private BigDecimal calculateActualCost(List<ElectricityReading> readings) {

        // average
        BigDecimal averageReading = readings.stream()
                .map(ElectricityReading::reading)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalEnergy = averageReading.multiply(BigDecimal.valueOf(WEEKLY_HOURS));

        BigDecimal cost = totalEnergy
    }
}
