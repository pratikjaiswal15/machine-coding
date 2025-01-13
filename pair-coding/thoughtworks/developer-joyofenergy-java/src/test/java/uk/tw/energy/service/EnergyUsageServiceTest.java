package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class EnergyUsageServiceTest {

    private AccountService accountService;
    private MeterReadingService meterReadingService;
    private EnergyUsageService energyUsageService;

    private final String SMART_METER_ID = "smart-meter-0";
    private final List<PricePlan> pricePlan = List.of(
            new PricePlan("plan-1", "supplier-name" , BigDecimal.valueOf(0.5), Collections.emptyList())
    );
//
//    private final PricePlan pricePlan = Arrays.asList(
//
//    )
    @BeforeEach
    void setup() {
        accountService = new AccountService(new HashMap<>());
        meterReadingService = new MeterReadingService(new HashMap<>());
        energyUsageService = new EnergyUsageService(accountService, meterReadingService);
    }

    @Test
    void calculateWeeklyCost() {
        Instant endDate = Instant.now();
        Instant startDate = endDate.minusSeconds(7 * 24 * 3600);

        List<ElectricityReading> readings = List.of(
                new ElectricityReading(startDate.plusSeconds(1000), BigDecimal.valueOf(0.2)),
                new ElectricityReading(startDate.plusSeconds(10000), BigDecimal.valueOf(0.3)),
                new ElectricityReading(endDate.minusSeconds(1000), BigDecimal.valueOf(0.4))
                );

        when(accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID)).thenReturn(pricePlan.get(0).getPlanName());
        when(meterReadingService.getReadings(SMART_METER_ID)).thenReturn(Optional.of(readings));


        BigDecimal cost = energyUsageService.calculateUsage(SMART_METER_ID, startDate, endDate);


        assertEquals("Calculated cost",cost, BigDecimal.valueOf(25.2));
    }
}
