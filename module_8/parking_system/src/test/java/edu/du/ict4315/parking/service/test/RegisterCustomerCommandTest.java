/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.service.test;

import edu.du.ict4315.parking.service.RegisterCustomerCommand;
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
        params.setProperty("firstName", "Jessica");
        params.setProperty("lastName", "Smith");
        params.setProperty("phoneNumber", "1 385 294 7164");
        params.setProperty("streetAddress1", "16 Main St");
        params.setProperty("city", "Denver");
        params.setProperty("state", "CO");
        params.setProperty("zip","");
        RegisterCustomerCommand instance = new RegisterCustomerCommand();
        
        //Test that zip code exception is thrown
        Throwable exception = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception.getMessage());
        
        //Set zip and change phone number to 10 digits, starting with 1
        params.setProperty("zip", "57295");
        params.setProperty("phoneNumber", "1473625947");
        
        //Test that invalid phone number exception is thrown
        Throwable exception2 = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception2.getMessage());
        
        //Set phone number to 9 digits and repeat test
        params.setProperty("phoneNumber", "473625947");
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
        params.setProperty("firstName", "Jessica");
        params.setProperty("lastName", "Smith");
        params.setProperty("phoneNumber", "1 385 294 7164");
        params.setProperty("streetAddress1", "16 Main St");
        params.setProperty("city", "Denver");
        params.setProperty("state", "CO");
        params.setProperty("zip", "35276");
        RegisterCustomerCommand instance = new RegisterCustomerCommand();
        String expResult = "CUST-12";
        String result = instance.execute(params);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCommandName method, of class RegisterCustomerCommand.
     */
    @Test
    public void testGetCommandName() {
        RegisterCustomerCommand instance = new RegisterCustomerCommand();
        String expResult = "CUSTOMER";
        String result = instance.getCommandName();
        assertEquals(expResult, result);
    }
    
}
