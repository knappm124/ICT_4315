/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingLotChargeOnEntry;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.factory.FactoryOriginalAlgorithm;
import edu.du.ict4315.parking.charges.factory.FactoryParkingChargeCalculator;
import edu.du.ict4315.parking.support.PropertiesUtilities;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class ParkCommandTest {

    private static RealParkingOffice parkingOffice;
    private RegisterCarCommand registerCarCommand;
    private static Customer customer;
    private static Car car;
    private static ParkingPermit permit;

    public ParkCommandTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        parkingOffice = new RealParkingOffice();
        Customer cust = new Customer();
        cust.setLastName("Smith");
        cust.setFirstName("Pat");
        cust.setPhoneNumber("303-555-1212");
        String id = parkingOffice.register(cust);
        customer = parkingOffice.getCustomer(id);
        Car vehicle = new Car();
        vehicle.setLicensePlate("ABC-123");
        vehicle.setType(CarType.COMPACT);
        vehicle.setOwner(customer);
        car = vehicle;
        String permitId = parkingOffice.register(vehicle);
        permit = parkingOffice.getParkingPermit(permitId);
    }

    @BeforeEach
    public void setUp() {
        ParkCommand command = new ParkCommand(parkingOffice);
    }

    @Test
    public void testGetCommandName() {
        System.out.println("getCommandName");
        ParkCommand command = new ParkCommand(parkingOffice);
        assertEquals("PARK", command.getCommandName());
    }

    @Test
    public void testGetDisplayName() {
        System.out.println("getDisplayName");
        ParkCommand command = new ParkCommand(parkingOffice);
        assertEquals("Park Car", command.getDisplayName());
    }

    @Test
    public void testExecute() {
        System.out.println("execute");
        String[] rows = {
            "permitid=" + permit.getId() + "\nlotid=Test\n",
            "permitid=" + permit.getId() + "\nlotid=Test\nlocaldatetime=2022-01-02T08:00:00\n",};

        for (String row : rows) {
            Properties params = PropertiesUtilities.loadProperties(row);
            ParkCommand instance = new ParkCommand(parkingOffice);

            car.setType(CarType.COMPACT);

            ParkingLot lot = new ParkingLotChargeOnEntry("Test", "Test",
                    new Address.Builder().build(), Money.of("$ 5.00"), 10);
            parkingOffice.register(lot);
            // ParkingLot lot = parkingOffice.getParkingLot("301");
            FactoryParkingChargeCalculator factory = lot.getParkingChargesCalculatorFactory();
            FactoryParkingChargeCalculator updateFactory = new FactoryOriginalAlgorithm();
            lot.setParkingChargeCalculatorFactory(updateFactory);

            System.out.println(lot);

            String expResult = Money.times(lot.getBaseRate(), 0.8).toString(); // 80% of base rate
            String result = "";
            result = instance.execute(params);
            System.out.println(expResult);

            assertEquals(expResult, result);

            car.setType(CarType.SUV);
            expResult = lot.getBaseRate().toString();

            System.out.println(expResult);
            result = instance.execute(params);

            assertEquals(expResult, result);
            lot.setParkingChargeCalculatorFactory(factory);
            parkingOffice.unregister(lot);

        }
    }

}
