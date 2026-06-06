/*
 * Course: ICT4315
 * File: FlatRateTest
 * Author: Instructor
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingLotChargeOnExit;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.factory.FactoryFlatRate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class FlatRateTest {

    private static RealParkingOffice office;
    private static Customer customer;
    private static Car car;
    private static ParkingPermit permit;
    private static ParkingLot lot;

    @BeforeAll
    public static void initialize() {
        Address address = new Address.Builder()
                .withStreetAddress1("123 Main St.")
                .withCity("Denver")
                .withState("CO")
                .withZip("80210")
                .build();
        office = new RealParkingOffice();
        customer = new Customer();
        customer.setFirstName("J");
        customer.setLastName("Roe");
        office.register(customer);
        car = new Car();
        car.setOwner(customer);
        car.setLicensePlate("ABC-123");
        car.setType(CarType.SUV);
        String permitId = office.register(car);
        permit = office.getParkingPermit(permitId);
        lot = new ParkingLotChargeOnExit("Test", "Test", address,
                Money.of("$ 10.00"), 10, new FactoryFlatRate()
        );
        office.register(lot);
        lot.setParkingChargeCalculatorFactory(new FactoryFlatRate());
    }

    @AfterAll
    public static void cleanup() {
        office.unregister(lot);
    }

    @Test
    public void testGetParkingCharge() {
        LocalDateTime entryTime = LocalDateTime.of(2022, 1, 17, 8, 0);
        Money baseRate = lot.getBaseRate();
        String[] exitTimes = {
            "2022-01-17T09:00", "2022-01-18T09:00", "2022-01-19T09:01",
            "2022-01-20T09:01", "2022-01-21T10:00"
        };
        String[] expResults = {
            
            "$ 10.00", "$ 20.00", "$ 30.00", "$ 40.00", "$ 50.00"
          // TODO: Until update with duration, all charges will be for 1 day.
          // "$ 10.00","$ 10.00","$ 10.00","$ 10.00","$ 10.00", 
        };
        ParkingTransaction pt;
        for (int i = 0; i < exitTimes.length; i += 1) {
            pt = office.park(entryTime, permit, lot);
            assertNull(pt);
            LocalDateTime exitTime = LocalDateTime.parse(exitTimes[i]);
            Money expectedResult = Money.of(expResults[i]);
            pt = office.leave(entryTime, exitTime, permit, lot);
            Money result = pt.getChargedAmount();
            // assertEquals(expectedResult, result);
            System.out.println(" Flat rate: Entry: "+entryTime+ 
                    "; Exit: "+exitTime+"; Charge: "+result);
        }

    }

    @Test
    public void testToString() {
        assertTrue(lot.getParkingChargesCalculatorFactory().getCalculator().toString().contains("Flat"));
    }

}
