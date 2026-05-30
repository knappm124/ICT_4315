/*
 * Course ICT4315
 * Author: Instructor
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.service.ParkingService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * How do we test the parking service? 1. Let's create a RealParkingOffice 2.
 * Let's create a parking service 3. Don't use a client and server--just invoke
 * the parking service directly!
 *
 * @author michael
 */
public class TestParkingService {

    private static RealParkingOffice office;
    private static ParkingService parkingService;

    @BeforeAll
    public static void first() {
        office = new RealParkingOffice();
        parkingService = new ParkingService(office);
    }

    // These are a little tricky because the Customer Ids are not fixed
    // until the registration happens.
    // So we register the Customer and then look hir up, and store the 
    // id in the right slot in the inputCN array.
    private static final String[] inputLN = {"Doe", "Roe", "Moe"};
    private static final String[] inputFN = {"Jane", "Richard", "Pat"};
    private static final String[] inputPN = {"303-555-1212", null, null};
    private static final String[] inputLP = {"ABC-123", "DEF-456", "GHI-789"};
    private static String[] inputCN = {"CUST-12", "CUST-12", "CUST-14"};

    private int getInputCustomerCount() {
        return inputLN.length;
    }

    private int getInputCarCount() {
        return inputLP.length;
    }

    private String getInputCustomer(int i) {
        if (i < 0 || i > inputLN.length) {
            return null;
        }
        return "CUSTOMER\nlastname=" + inputLN[i] + "\nfirstname=" + inputFN[i] + "\n"
                + (inputPN == null ? "" : ("phonenumber=" + inputPN[i] + "\n")) + "end\n";
    }

    private String getInputCar(int i) {
        if (i < 0 || i > inputLN.length) {
            return null;
        }
        return "CAR\nlicense=" + inputLP[i] + "\ncustomer=" + inputCN[i] + "\nend\n";
    }

    private static final String[] badInput = {
        "CUSTOMER\nname=Doe\nend\n",
        "CUSTOMER\nfirstname=Richard\nend\n",
        "CUSTOMER\nlastname:Moe\nfirstname=Pat\nend\n",
        "CAR\nend\n",
        "CAR\nlicense=ABC-123\nend\n",
        "CAR\ncustomer=NONE\nend\n",
        "CAR\nlicense=ABC-123\ncustomer=NONE\nend\n"
    };

    @Test
    public void testGoodInput() throws IOException {
        for (int i = 0; i < getInputCustomerCount(); i += 1) {
            String s = getInputCustomer(i);
            System.out.println("Testing " + s);
            InputStream is = new ByteArrayInputStream(s.getBytes());
            String result = parkingService.handleInput(is);
            is.close();
            Customer c = office.getCustomer(result);
            assertNotNull(c);
            assertEquals(inputLN[i], c.getLastName());
            inputCN[i] = c.getId(); // Update Customer ID to actual id
            System.out.println("Result: " + result);
        }
        for (int i = 0; i < getInputCarCount(); i += 1) {
            String s = getInputCar(i);
            InputStream is = new ByteArrayInputStream(s.getBytes());
            String result = parkingService.handleInput(is);
            is.close();
            ParkingPermit permit = office.getParkingPermit(result);
            assertNotNull(permit);
            assertEquals(permit.getCar().getOwner().getId(), inputCN[i]);
            System.out.println("Result: " + result);
        }
    }

    @Test
    public void testBadInput() throws IOException {
        for (int i = 0; i < badInput.length; i += 1) {
            String s = badInput[i];
            InputStream is = new ByteArrayInputStream(s.getBytes());
            // Some throw IllegalArgumentException; some NullPointerException
            assertThrows(Exception.class,
                    () -> parkingService.handleInput(is), "No exception thrown!");
            is.close();
        }
    }
}
