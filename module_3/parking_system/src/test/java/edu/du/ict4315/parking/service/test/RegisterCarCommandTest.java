/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.service.test;

import edu.du.ict4315.parking.service.RegisterCarCommand;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 */
public class RegisterCarCommandTest {

     /**
     * Test of checkParameters method, of class RegisterCarCommand.
     */
    @Test
    public void testCheckParameters() {
        //Create properties object and set customer id, car type, and license plate
        //Setting car type to invalid type
        Properties params = new Properties();
        params.setProperty("customerId", "CUST-11");
        params.setProperty("carType","Sedan");
        params.setProperty("licensePlate","D3GS 43FD");
        RegisterCarCommand instance = new RegisterCarCommand();

        //Test that car type exception is thrown
        Throwable exception = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Car must be of type SUV or Compact", exception.getMessage());
        
        //Set car type to a valid  type and change license plate to be empty
        //Then run test to confirm exception is thrown
        params.setProperty("carType","SUV");
        params.setProperty("licensePlate","");
        Throwable exception2 = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("License plate cannot be empty", exception2.getMessage());
    }
    
        /**
     * Test of execute method, of class RegisterCarCommand.
     */
    @Test
    public void testExecute() {
        Properties params = new Properties();
        params.setProperty("customerId", "CUST-11");
        params.setProperty("carType","SUV");
        params.setProperty("licensePlate","D3GS 43FD");
        RegisterCarCommand instance = new RegisterCarCommand();
        String expResult = "P1001";
        String result = instance.execute(params);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getCommandName method, of class RegisterCarCommand.
     */
    @Test
    public void testGetCommandName() {
        RegisterCarCommand instance = new RegisterCarCommand();
        String expResult = "CAR";
        String result = instance.getCommandName();
        assertEquals(expResult, result);
    }
}
