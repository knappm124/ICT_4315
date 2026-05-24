package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.strategy.DesignatedDayNineToSix;
import edu.du.ict4315.parking.charges.strategy.FavorLargeCarsLateEntry;
import edu.du.ict4315.parking.charges.strategy.FavorSmallCarsEarlyIn;
import edu.du.ict4315.parking.charges.strategy.FlatRate;
import edu.du.ict4315.parking.charges.strategy.OriginalAlgorithm;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParkingChargeStrategyTest {

    private ParkingChargeStrategy strategy1 = new DesignatedDayNineToSix();
    private ParkingChargeStrategy strategy2 = new FavorLargeCarsLateEntry();
    private ParkingChargeStrategy strategy3 = new FavorSmallCarsEarlyIn();
    private ParkingChargeStrategy strategy4 = new FlatRate();
    private ParkingChargeStrategy strategy5 = new OriginalAlgorithm();

    private static RealParkingOffice office = new RealParkingOffice();

    private static Car car1;
    private static Car car2;
    private static Customer customer;
    private static ParkingPermit permit1;
    private static ParkingPermit permit2;
    private static ParkingLot lot;

    @BeforeAll
    public static void setup() throws Exception {
        System.out.println("Beginning setup");
        customer = new Customer();

        car1 = new Car();
        car1.setOwner(customer);
        car1.setType(CarType.SUV);

        car2 = new Car();
        car2.setOwner(customer);
        car2.setType(CarType.COMPACT);
        System.out.println("Car 1: " + car1);
        System.out.println("Car 2: " + car2);

        String permit1_id = office.register(car1);
        permit1 = office.getParkingPermit(permit1_id);

        String permit2_id = office.register(car2);
        permit2 = office.getParkingPermit(permit2_id);

        office.register(customer);

        lot = office.getParkingLot("W");
        System.out.println("Parking Lot\n" + lot);

        System.out.println("Setup complete");
    }

    /*
   * This method calls for the rate and compares it to the expected result
     */
    private void testStrategyWithData(ParkingChargeStrategy strategy, Money baseRate, LocalDateTime time,
            ParkingPermit permit, Money expectedResult) {
        Money money = strategy.getParkingCharge(baseRate, time, Duration.ofHours(1), permit);
        assertEquals(expectedResult, money);
    }

    // TODO: Make workhorse generic with parameters. Add data
    // TODO: Modify office to support strategies.
    @Test
    final void testStrategy1() {

        LocalDateTime[] timesToTest = {
            LocalDateTime.of(2022, 2, 1, 6, 30),
            LocalDateTime.of(2022, 7, 4, 6, 30),
            LocalDateTime.of(2022, 7, 4, 11, 25),
            LocalDateTime.of(2022, 7, 4, 20, 00)
        };

        // Both cars should have the same results for strategy 1
        Money results[] = {
            lot.getBaseRate(), // Regular rate
            Money.times(lot.getBaseRate(), 2), // Double rate
            Money.add(Money.div(lot.getBaseRate(), 4), Money.times(lot.getBaseRate(), 2)),
            Money.times(lot.getBaseRate(), 2), // Double rate
        };

        System.out.println(strategy1);
        System.out.println("Lot " + lot.getId() + " base rate is " + lot.getBaseRate());

        for (int test = 0; test < timesToTest.length; test++) {
            testStrategyWithData(strategy1, lot.getBaseRate(), timesToTest[test], permit1, results[test]);
            testStrategyWithData(strategy1, lot.getBaseRate(), timesToTest[test], permit2, results[test]);
        }
    }

    @Test
    final void testStrategy2() {
        LocalDateTime[] timesToTest = {
            LocalDateTime.of(2020, 2, 1, 6, 30),
            LocalDateTime.of(2020, 7, 4, 6, 30),
            LocalDateTime.of(2020, 7, 4, 11, 25),
            LocalDateTime.of(2020, 7, 4, 20, 00)
        };

        Money results[][] = {
            {Money.of(4.00), Money.of(5.00)},
            {Money.of(4.00), Money.of(5.00)},
            {Money.of(4.00), Money.of(5.00)},
            {Money.of(2.80), Money.of(3.50)},};

        System.out.println(strategy2);
        System.out.println("Lot W base rate is " + lot.getBaseRate());

        for (int test = 0; test < timesToTest.length; test++) {
            testStrategyWithData(strategy2, lot.getBaseRate(), timesToTest[test], permit1, results[test][0]);
            testStrategyWithData(strategy2, lot.getBaseRate(), timesToTest[test], permit2, results[test][1]);
        }
    }

    @Test
    final void testStrategy3() {
        LocalDateTime[] timesToTest = {
            LocalDateTime.of(2020, 2, 1, 6, 30),
            LocalDateTime.of(2020, 7, 4, 6, 30),
            LocalDateTime.of(2020, 7, 4, 11, 25),
            LocalDateTime.of(2020, 7, 4, 20, 00)
        };

        Money results[][] = {
            {Money.of(4.50), Money.of(3.60)},
            {Money.of(4.50), Money.of(3.60)},
            {Money.of(5.00), Money.of(4.00)},
            {Money.of(5.00), Money.of(4.00)},};

        System.out.println(strategy3);
        System.out.println("Lot W base rate is " + lot.getBaseRate());

        for (int test = 0; test < timesToTest.length; test++) {
            testStrategyWithData(strategy3, lot.getBaseRate(), timesToTest[test], permit1, results[test][0]);
            testStrategyWithData(strategy3, lot.getBaseRate(), timesToTest[test], permit2, results[test][1]);
        }
    }

    @Test
    final void testStrategy4() {
        LocalDateTime[] timesToTest = {
            LocalDateTime.of(2020, 2, 1, 6, 30),
            LocalDateTime.of(2020, 7, 4, 6, 30),
            LocalDateTime.of(2020, 7, 4, 11, 25),
            LocalDateTime.of(2020, 7, 4, 20, 00)
        };

        Money results[][] = {
            {Money.of(5.00), Money.of(5.00)},
            {Money.of(5.00), Money.of(5.00)},
            {Money.of(5.00), Money.of(5.00)},
            {Money.of(5.00), Money.of(5.00)},};

        System.out.println(strategy4);
        System.out.println("Lot W base rate is " + lot.getBaseRate());

        for (int test = 0; test < timesToTest.length; test++) {
            testStrategyWithData(strategy4, lot.getBaseRate(), timesToTest[test], permit1, results[test][0]);
            testStrategyWithData(strategy4, lot.getBaseRate(), timesToTest[test], permit2, results[test][1]);
        }
    }

    @Test
    final void testStrategy5() {
        LocalDateTime[] timesToTest = {
            LocalDateTime.of(2022, 2, 1, 6, 30),
            LocalDateTime.of(2022, 7, 4, 6, 30),
            LocalDateTime.of(2022, 7, 4, 11, 25),
            LocalDateTime.of(2022, 7, 4, 20, 00)
        };

        Money results[][] = {
            {Money.of(5.00), Money.of(4.00)},
            {Money.of(5.00), Money.of(4.00)},
            {Money.of(5.00), Money.of(4.00)},
            {Money.of(5.00), Money.of(4.00)},};

        System.out.println(strategy5);
        System.out.println("Lot W base rate is " + lot.getBaseRate());

        for (int test = 0; test < timesToTest.length; test++) {
            testStrategyWithData(strategy5, lot.getBaseRate(), timesToTest[test], permit1, results[test][0]);
            testStrategyWithData(strategy5, lot.getBaseRate(), timesToTest[test], permit2, results[test][1]);
        }
    }
}
