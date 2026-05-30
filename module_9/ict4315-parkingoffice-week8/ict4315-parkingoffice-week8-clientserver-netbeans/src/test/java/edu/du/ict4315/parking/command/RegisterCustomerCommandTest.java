/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.command.RegisterCustomerCommand;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.support.PropertiesUtilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author michael
 */
public class RegisterCustomerCommandTest {

    private static RealParkingOffice parkingOffice;

    @BeforeAll
    private static void setUp() {
        parkingOffice = new RealParkingOffice();
    }

    private RegisterCustomerCommand customerCommand;

    @BeforeEach
    private void initialize() {
        customerCommand = new RegisterCustomerCommand(parkingOffice);
    }

    @Test
    public void testGetCommandName() {
        System.out.println("getCommandName");
        assertEquals(customerCommand.getCommandName(), "CUSTOMER");
    }

    @Test
    public void testGetDisplayName() {
        System.out.println("getDisplayName");
        assertEquals(customerCommand.getDisplayName(), "Register Customer");
    }

    private static final String[][] propLoadGood = {
        {"lastname:Smith\n", "Smith", "", ""},
        {"lastname=Smith\nfirstname=Sam\n", "Smith", "Sam", ""},
        {"lastname=Smith\nfirstname=Sam\nphonenumber=303-555-1212\n", "Smith", "Sam", "303-555-1212"}
    };

    @Test
    public void testExecute() {
        System.out.println("execute");
        for (String[] row : propLoadGood) {
            Properties props = PropertiesUtilities.loadProperties(row[0]);
            String result = customerCommand.execute(props);
            // result is a customerId.
            Customer customer = parkingOffice.getCustomer(result);
            System.out.println(customer);
            assertEquals(customer.getLastName(), row[1]);
            assertEquals(customer.getFirstName(), row[2]);
            assertEquals(customer.getPhoneNumber(), row[3]);
        }
    }

}
