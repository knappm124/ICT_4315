/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.parking;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.charges.factory.FactoryOriginalAlgorithm;
import edu.du.ict4315.parking.charges.factory.FactoryParkingChargeCalculator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO: This test is not sufficient nor accurate for replaceable strategies.
 *
 * @author michael
 */
public class TransactionManagerTest {

    private static RealParkingOffice parkingOffice;
    private static TransactionManager transactionManager;
    private static Car car;
    private static Customer customer;
    private static ParkingPermit permit;

    public TransactionManagerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        parkingOffice = new RealParkingOffice();
        transactionManager = new TransactionManager(parkingOffice);
        customer = new Customer();
        customer.setFirstName("J");
        customer.setLastName("Doe");
        Address address = new Address.Builder()
                .withStreetAddress1("101 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80210")
                .build();
        customer.setAddress(address);
        customer.setPhoneNumber("303-555-1212");
        parkingOffice.register(customer);
        car = new Car(CarType.COMPACT, "123-ABC", customer);
        String permitId = parkingOffice.register(car);
        permit = parkingOffice.getParkingPermit(permitId);
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testPark() {
        System.out.println("Park");
        LocalDateTime timeIn = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 35);
        LocalDateTime timeOut = LocalDateTime.of(2022, Month.JANUARY, 1, 13, 40);
        ParkingLot lot = new ParkingLotChargeOnEntry("Test", "Test",
                new Address.Builder().build(), Money.of("$ 5.00"), 50,
                new FactoryOriginalAlgorithm());
        parkingOffice.register(lot);

        FactoryParkingChargeCalculator factory = lot.getParkingChargesCalculatorFactory();
        FactoryParkingChargeCalculator updateFactory = new FactoryOriginalAlgorithm();
        lot.setParkingChargeCalculatorFactory(updateFactory);

        ParkingTransaction result1 = transactionManager.park(timeIn, permit, lot);
        ParkingTransaction result2 = transactionManager.leave(timeIn, timeOut, permit, lot);

        // result1 should have the charge; result2 should be empty.
        if (result1 == null) {
            System.out.println("ERROR");
        } else {
            System.out.println("Park: " + result1);

            Money expectedAmt = lot.getBaseRate(); // one day (pay on entry lot)
            if (car.getType() == CarType.COMPACT) {
                expectedAmt = Money.div(Money.times(expectedAmt, 4), 5);
            }
            assertEquals(expectedAmt, result1.getChargedAmount());
        }

        if (result2 != null) {
            System.out.println("ERROR2");
        }

        lot.setParkingChargeCalculatorFactory(factory);
        parkingOffice.unregister(lot);

    }

    @Test
    public void testParkAndLeave() {
        System.out.println("Park and Leave");
        LocalDateTime timeIn = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 35);
        LocalDateTime timeOut = LocalDateTime.of(2022, Month.JANUARY, 1, 13, 40);
        ParkingLot lot = new ParkingLotChargeOnExit("Test", "Test",
                new Address.Builder().build(), Money.of("$ 5.00"), 50);
        parkingOffice.register(lot);
        // parkingOffice.getParkingLot("301");

        // For this test, ensure the original factory is in place
        FactoryParkingChargeCalculator factory = lot.getParkingChargesCalculatorFactory();
        FactoryParkingChargeCalculator updateFactory = new FactoryOriginalAlgorithm();
        lot.setParkingChargeCalculatorFactory(updateFactory);

        ParkingTransaction result1 = transactionManager.park(timeIn, permit, lot);
        ParkingTransaction result2 = transactionManager.leave(timeIn, timeOut, permit, lot);
        Duration duration = Duration.between(timeIn, timeOut);

        System.out.println("Park: " + result1);
        System.out.println("    Rate: " + lot.getBaseRate() + "; Duration: " + duration);
        if (result2 == null) {
            Money expectedAmt = lot.getBaseRate();
            if (car.getType() == CarType.COMPACT) {
                expectedAmt = Money.div(Money.times(expectedAmt, 4), 5);
            }
            if (duration.compareTo(Duration.ofDays(1L)) > 0) {
                expectedAmt = Money.times(expectedAmt, (int) (duration.toDays()));
            }
            assertEquals(expectedAmt, result1.getChargedAmount());
        }

        // Restore the factory
        lot.setParkingChargeCalculatorFactory(factory);
        // Unregister the lot
        parkingOffice.unregister(lot);

        System.out.println("Leave: " + result2);
        if (result2 != null) {
            // TODO: add unit test for pay-on-exit lots
        }

    }

    @Test
    public void testGetParkingCharges_Customer() {
        System.out.println("getParkingCharges: Customer");

        LocalDateTime timeIn = LocalDateTime.of(2022, Month.JANUARY, 3, 12, 35);
        LocalDateTime timeOut = LocalDateTime.of(2022, Month.JANUARY, 3, 13, 40);
        ParkingLot l = parkingOffice.getParkingLot("301");
        ParkingTransaction result1 = transactionManager.park(timeIn, permit, l);
        ParkingTransaction result2 = transactionManager.leave(timeIn, timeOut, permit, l);

        Money expResult = null;
        Money result = transactionManager.getParkingCharges(customer);
        System.out.println("Customer charges: " + result);
        // assertEquals(expResult, result);
        // fail("The test case is a prototype.");
    }

    @Test
    public void testGetParkingCharges_ParkingPermit() {
        System.out.println("getParkingCharges: ParkingPermit");

        LocalDateTime timeIn = LocalDateTime.of(2022, Month.JANUARY, 6, 12, 35);
        LocalDateTime timeOut = LocalDateTime.of(2022, Month.JANUARY, 6, 13, 40);
        ParkingLot l = parkingOffice.getParkingLot("301");
        ParkingTransaction result1 = transactionManager.park(timeIn, permit, l);
        ParkingTransaction result2 = transactionManager.leave(timeIn, timeOut, permit, l);

        Money expResult = null;
        Money result = transactionManager.getParkingCharges(permit);
        System.out.println("Permit charges: " + result);
        // assertEquals(expResult, result);
        // fail("The test case is a prototype.");
    }

}
