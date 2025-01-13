package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnergyUsageServiceTest {

    private MeterReadingService meterReadingService;
    private AccountService accountService;
    private List<PricePlan> pricePlans;
    private static final String SMART_METER_ID = "smart-meter-id";
    private static final String PLAN_ID = "plan-A"
//    private final MeterReadingService meterReadingService = mock(MeterReadingService.class);
//    private final AccountService accountService = mock(AccountService.class);
//    private final List<PricePlan> pricePlans = List.of(
//            new PricePlan(
//                    "Plan A",
//                    "Supplier A",
//                    BigDecimal.valueOf(0.2),
//                    List.of() // Empty list for peakTimeMultipliers, if none
//            )
//    );

    private final EnergyUsageService energyUsageService = new EnergyUsageService(meterReadingService, accountService, pricePlans);

    @BeforeEach
    void setup() {
        meterReadingService = new MeterReadingService(new HashMap<>());
        accountService = new AccountService(new HashMap<>());
        pricePlans = List.of(
                new PricePlan(PLAN_ID, "Supplier A", BigDecimal.valueOf(0.2), Collections.emptyList())
        );
    }
    @Test
    void calculateCost_shouldReturnCorrectCost() {
//        String smartMeterId = "smart-meter-0";
//
//        // Use the actual range of the readings
//        Instant startDate = Instant.now().minusSeconds(7 * 24 * 3600); // One week ago
//        Instant endDate = Instant.now(); // Current time
//
//        List<ElectricityReading> readings = List.of(
//                new ElectricityReading(startDate, BigDecimal.valueOf(0.2049)),
//                new ElectricityReading(endDate, BigDecimal.valueOf(0.9507))
//                // Include all readings as per your provided data
//        );
//
//        when(meterReadingService.getReadings(smartMeterId)).thenReturn(Optional.of(readings));
//        when(accountService.getPricePlanIdForSmartMeterId(smartMeterId)).thenReturn("Plan A");
//
//        BigDecimal cost = energyUsageService.calculateUsageCost(smartMeterId, startDate, endDate);
//        System.out.println("Calculated cost: " + cost); // Debugging output
//        assertEquals(BigDecimal.valueOf(200) ,cost); // Ensure cost is calculated

        Instant endDate = Instant.now();

        Instant startDate = endDate.minusSeconds(7 * 24 * 3600);


        List<ElectricityReading> readings = List.of(
                new ElectricityReading(startDate, BigDecimal.valueOf(0.24)),
                new ElectricityReading(endDate, BigDecimal.valueOf(0.4))
        );

        when(meterReadingService.getReadings(SMART_METER_ID)).thenReturn(Optional.of(readings));
        when(accountService.getPricePlanIdForSmartMeterId(SMART_METER_ID)).thenReturn(PLAN_ID);


        BigDecimal cost = energyUsageService.calculateUsageCost(SMART_METER_ID, startDate, endDate);

        assertEquals(BigDecimal.valueOf(200), cost);
    }


}
