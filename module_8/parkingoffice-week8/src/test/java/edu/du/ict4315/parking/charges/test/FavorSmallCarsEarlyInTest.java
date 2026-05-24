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
import edu.du.ict4315.parking.charges.strategy.FavorSmallCarsEarlyIn;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class FavorSmallCarsEarlyInTest {

    ParkingChargeStrategy strategy = new FavorSmallCarsEarlyIn();
    Car car = new Car();
    ParkingPermit permit = new ParkingPermit("AAA", car, LocalDateTime.of(2022, 1, 3, 8, 0, 0));

    // 20% discount for COMPACT cars for the whole stay
    // 10% off for early in (before 8 am) for the whole stay
    @Test
    void chargeTest() {
        Money baseRate = Money.of("$ 10.00");

        String[] dates = {
            "2022-01-03T09:00:00",
            "2022-01-29T06:30:00",
            "2022-01-29T07:50:00",};

        long[] durations = {
            1, 1, 24
        };
        Money[] expectedChargeSUV = {
            baseRate,
            Money.div(Money.times(baseRate, 9), 10),
            Money.times(Money.div(Money.times(baseRate, 9), 10), 2),};

        Money[] expectedChargeCompact = {
            Money.div(Money.times(baseRate, 4), 5),
            Money.div(Money.times(Money.div(Money.times(baseRate, 4), 5), 9), 10),
            Money.times(Money.div(Money.times(Money.div(Money.times(baseRate, 4), 5), 9), 10), 2),};

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
            assertEquals(expectedChargeCompact[i], result);
        }
    }
}
