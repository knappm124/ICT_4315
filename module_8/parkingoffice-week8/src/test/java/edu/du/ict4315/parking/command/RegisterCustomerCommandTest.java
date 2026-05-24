/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.RealParkingOffice;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 */
public class RegisterCustomerCommandTest {
    
    /**
     * Test of checkParameters method, of class RegisterCustomerCommand.
     * @throws java.io.IOException
     */
    @Test
    public void testCheckParameters() throws IOException {
        //Create properties object with all parameters except zip
        Properties params = new Properties();
        params.setProperty("firstname", "Jessica");
        params.setProperty("lastname", "Smith");
        params.setProperty("phonenumber", "1 385 294 7164");
        params.setProperty("streetaddress1", "16 Main St");
        params.setProperty("city", "Denver");
        params.setProperty("state", "CO");
        params.setProperty("zip","57295");
        RealParkingOffice office = new RealParkingOffice();
        RegisterCustomerCommand instance = new RegisterCustomerCommand(office);

        
        //Set phone number to 10 digits, starting with 1
        params.setProperty("phonenumber", "1473625947");
        
        //Test that invalid phone number exception is thrown
        Throwable exception2 = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception2.getMessage());
        
        //Set phone number to 9 digits and repeat test
        params.setProperty("phonenumber", "473625947");
        Throwable exception3 = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception3.getMessage());
    }

    /**
     * Test of execute method, of class RegisterCustomerCommand.
     */
    @Test
    public void testExecute() {
        Properties params = new Properties();
        params.setProperty("firstname", "Jessica");
        params.setProperty("lastname", "Smith");
        params.setProperty("phonenumber", "1 385 294 7164");
        params.setProperty("streetaddress1", "16 Main St");
        params.setProperty("city", "Denver");
        params.setProperty("state", "CO");
        params.setProperty("zip", "35276");
        RealParkingOffice office = new RealParkingOffice();
        RegisterCustomerCommand instance = new RegisterCustomerCommand(office);
        String expResult1 = "CUST2"; //Running test file by itself
        String expResult2 = "CUST7"; //Running all test files
        String result = instance.execute(params);
        System.out.println("HERE: " + result);
        assertTrue(expResult1.equals(result)||expResult2.equals(result));
    }

    /**
     * Test of getCommandName method, of class RegisterCustomerCommand.
     */
    @Test
    public void testGetCommandName() {
        RealParkingOffice office = new RealParkingOffice();
        RegisterCustomerCommand instance = new RegisterCustomerCommand(office);
        String expResult = "CUSTOMER";
        String result = instance.getCommandName();
        assertEquals(expResult, result);
    }
    
}
