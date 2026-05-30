/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.PrimeTimeSurchargeNoWeekendCharge;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class PrimeTimeSurchargeNoWeekendChargeTest {

    ParkingChargeStrategy strategy = new PrimeTimeSurchargeNoWeekendCharge();
    Car car = new Car();
    ParkingPermit permit = new ParkingPermit("AAA", car, LocalDateTime.of(2023, 1, 3, 8, 0, 0));

    @Test
    void chargeTest() {
        Money baseRate = Money.of("$ 10.00");
        String[] dates = {
            "2022-01-03T08:00:00",
            "2022-01-29T10:00:00",
            "2022-01-28T12:00:00"
        };
        long[] durations = {
            7 * 24 - 9, 2 * 24 - 11, 1 * 24 - 13
        };
        Money[] expectedChargeSUV = {
            Money.times(baseRate, 5),
            Money.of("$ 0.00"),
            Money.div(Money.times(baseRate, 5), 4)
        };

        Money[] expectedChargeCompact = {
            Money.div(Money.times(Money.times(baseRate, 5), 4), 5),
            Money.of("$ 0.00"),
            baseRate // (5/4)*baseRate*(4/5)
        };

        // SUV test
        car.setType(CarType.SUV);
        for (int i = 0; i < dates.length; i++) {
            LocalDateTime ldt = LocalDateTime.parse(dates[i]);
            Duration duration = Duration.ofHours(durations[i]);
            Money result = strategy.getParkingCharge(baseRate, ldt, duration, permit);
            assertEquals(expectedChargeSUV[i], result);
        }
        // COMPACT test
        car.setType(CarType.COMPACT);
        for (int i = 0; i < dates.length; i++) {
            LocalDateTime ldt = LocalDateTime.parse(dates[i]);
            Duration duration = Duration.ofHours(durations[i]);
            Money result = strategy.getParkingCharge(baseRate, ldt, duration, permit);
            System.out.println("Index " + i);
            assertEquals(expectedChargeCompact[i], result);
        }
    }
}
