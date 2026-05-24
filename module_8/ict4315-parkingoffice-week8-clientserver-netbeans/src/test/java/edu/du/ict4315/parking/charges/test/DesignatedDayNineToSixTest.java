/*
 * Course: ICT4315
 * File: DesignatedDayNineToSixTest.java
 * Author: Instructor
 */
package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.strategy.DesignatedDayNineToSix;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class DesignatedDayNineToSixTest {

    ParkingChargeStrategy strategy = new DesignatedDayNineToSix();
    Car car = new Car();
    ParkingPermit permit = new ParkingPermit("AAA", car, LocalDateTime.of(2023, 1, 3, 8, 0, 0));

    @Test
    void chargeTest() {
        System.out.println("ParkingCharges: DesignatedDayNineToSix");
        final Money baseRate = Money.of("$ 10.00");
        // Test all designatedDates and a few others
        String[][] startDates = {
            {"2022-01-03T08:00:00", "2022-01-03T09:00:00"}, // normal
            {"2022-01-29T10:00:00", "2022-01-29T17:00:00"}, // + 25%
            {"2022-01-01T07:00:00", "2022-01-01T17:00:00"}, // double
            {"2022-01-01T11:00:00", "2022-01-01T12:00:00"}, // double + 25%
            {"2022-01-17T05:00:00", "2022-01-17T11:00:00"}, // double
            {"2022-02-21T13:00:00", "2022-02-21T13:30:00"}, // double + 25%

            {"2022-05-29T12:00:00", "2022-05-31T12:00:00"}, // + 25% 1st, 2x 2nd, normal
            {"2022-06-20T12:00:00", "2022-06-20T12:00:00"}, // double + 25%
            {"2022-07-02T12:00:00", "2022-07-05T12:00:00"}, // + 25% + double on 4th
            {"2022-09-05T09:05:00", "2022-09-05T09:08:00"}, // double + 25%
            {"2022-10-09T07:00:00", "2022-10-10T07:00:00"}, // normal + double 
            {"2022-10-10T07:00:00", "2022-10-10T17:00:00"}, // double

            {"2022-11-11T19:00:00", "2022-11-11T21:00:00"}, // double
            {"2022-12-24T20:00:00", "2022-12-27T20:00:00"}, // double + double + normal + normal
            {"2022-01-09T08:00:00", "2022-01-09T11:00:00"}, // normal
            {"2022-01-29T12:00:00", "2022-01-29T12:15:00"}, // + 25%
        };
        Money normal = baseRate;
        Money a25 = Money.div(baseRate, 4); // added 25%
        Money p25 = Money.div(Money.times(baseRate, 5), 4); // base + 25%
        Money m2 = Money.times(baseRate, 2);
        Money m2p25 = Money.add(Money.times(baseRate, 2), a25);
        Money sp1 = Money.add(Money.add(Money.div(Money.times(baseRate, 5), 4), m2), normal);
        Money sp2 = Money.add(Money.add(Money.times(normal, 2), p25), m2);
        Money sp3 = Money.add(Money.times(normal, 1), m2);
        Money sp4 = Money.add(Money.add(Money.times(normal, 2), m2), m2);

        Money[] expectedCharge = {
            normal, p25, m2, m2p25, m2, m2p25,
            sp1, m2p25, sp2, m2p25, sp3, m2,
            m2, sp4, normal, p25
        };

        // SUV test
        car.setType(CarType.SUV);
        for (int i = 0; i < startDates.length; i++) {
            LocalDateTime start = LocalDateTime.parse(startDates[i][0]);
            LocalDateTime stop = LocalDateTime.parse(startDates[i][1]);
            Duration duration = Duration.between(start, stop);
            Money result = strategy.getParkingCharge(baseRate, start, duration, permit);
            System.out.println("Test (" + i + ") " + start + " to " + stop + "; result: " + result + " (" + expectedCharge[i] + ")");
            assertEquals(expectedCharge[i], result);
        }
        // COMPACT test
        car.setType(CarType.COMPACT);
        for (int i = 0; i < startDates.length; i++) {
            LocalDateTime start = LocalDateTime.parse(startDates[i][0]);
            LocalDateTime stop = LocalDateTime.parse(startDates[i][1]);
            Duration duration = Duration.between(start, stop);
            Money result = strategy.getParkingCharge(baseRate, start, duration, permit);
            assertEquals(expectedCharge[i], result);
        }
    }
}
