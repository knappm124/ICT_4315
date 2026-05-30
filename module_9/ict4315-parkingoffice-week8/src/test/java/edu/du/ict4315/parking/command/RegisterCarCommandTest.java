/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.command.RegisterCarCommand;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.support.PropertiesUtilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class RegisterCarCommandTest {

    private static RealParkingOffice parkingOffice;
    private RegisterCarCommand registerCarCommand;
    private static Customer customer;

    @BeforeAll
    public static void setUpClass() {
        parkingOffice = new RealParkingOffice();
        Customer cust = new Customer();
        cust.setLastName("Smith");
        cust.setFirstName("Pat");
        cust.setPhoneNumber("303-555-1212");
        String id = parkingOffice.register(cust);
        customer = parkingOffice.getCustomer(id);
    }

    @BeforeEach
    public void setUp() {
        registerCarCommand = new RegisterCarCommand(parkingOffice);
    }

    @Test
    public void testGetCommandName() {
        System.out.println("getCommandName");
        assertEquals(registerCarCommand.getCommandName(), "CAR");
    }

    @Test
    public void testGetDisplayName() {
        System.out.println("getDisplayName");
        assertEquals(registerCarCommand.getDisplayName(), "Register Car");
    }

    @Test
    public void testExecute() {
        System.out.println("execute");
        String row = "license:ABC-123\ncustomer=" + customer.getId() + "\n";

        Properties props = PropertiesUtilities.loadProperties(row);

        String result = registerCarCommand.execute(props);
        // result is a permit id.
        Car car = parkingOffice.getParkingPermit(result).getCar();
        System.out.println(car);
        assertEquals(car.getOwner().getLastName(), customer.getLastName());
        assertEquals(car.getOwner().getFirstName(), customer.getFirstName());
        assertEquals(car.getOwner().getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(car.getOwner().getId(), customer.getId());
    }

    @Test
    public void testExecuteException() {
        System.out.println("executeException");
        String[] rows = {
            "customer=" + customer.getId() + "\n",
            "license:ABC-123\n",};

        for (String row : rows) {
            Properties props = PropertiesUtilities.loadProperties(row);
            assertThrows(IllegalArgumentException.class,
                    () -> registerCarCommand.execute(props), "No exception thrown!");
        }
    }
}
