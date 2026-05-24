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
import edu.du.ict4315.parking.charges.strategy.NoWeekendCharge;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class NoWeekendChargeTest {

    ParkingChargeStrategy strategy = new NoWeekendCharge();
    Car car = new Car();
    ParkingPermit permit = new ParkingPermit("AAA", car, LocalDateTime.of(2023, 1, 3, 8, 0, 0));

    @Test
    void chargeTest() {
        Money baseRate = Money.of("$ 10.00");
        String[] startDates = {
            "2022-01-03T08:00:00",
            "2022-01-29T10:00:00"
        };
        String[] endDates = {
            "2022-01-09T08:00:00",
            "2022-01-29T12:00:00"
        };

        Money[] expectedChargeSUV = {
            Money.times(baseRate, 5),
            Money.of("$ 0.00")
        };

        Money[] expectedChargeCompact = {
            Money.div(Money.times(Money.times(baseRate, 5), 4), 5),
            Money.of("$ 0.00")
        };

        // SUV test
        car.setType(CarType.SUV);
        for (int i = 0; i < startDates.length; i++) {
            LocalDateTime start = LocalDateTime.parse(startDates[i]);
            LocalDateTime stop = LocalDateTime.parse(endDates[i]);
            Duration duration = Duration.between(start, stop);
            Money result = strategy.getParkingCharge(baseRate, start, duration, permit);
            assertEquals(expectedChargeSUV[i], result);
        }
        // COMPACT test
        car.setType(CarType.COMPACT);
        for (int i = 0; i < startDates.length; i++) {
            LocalDateTime start = LocalDateTime.parse(startDates[i]);
            LocalDateTime stop = LocalDateTime.parse(endDates[i]);
            Duration duration = Duration.between(start, stop);
            Money result = strategy.getParkingCharge(baseRate, start, duration, permit);
            assertEquals(expectedChargeCompact[i], result);
        }
    }
}
