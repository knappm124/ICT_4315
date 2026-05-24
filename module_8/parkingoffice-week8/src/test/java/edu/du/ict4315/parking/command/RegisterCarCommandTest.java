/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.RealParkingOffice;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
        params.setProperty("customer", "CUST-11");
        params.setProperty("cartype","Sedan");
        params.setProperty("license","D3GS 43FD");
        RealParkingOffice office = new RealParkingOffice();
        RegisterCarCommand instance = new RegisterCarCommand(office);

        //Test that car type exception is thrown
        Throwable exception = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception.getMessage());
        
        //Set car type to a valid  type and change license plate to be empty
        //Then run test to confirm exception is thrown
        params.setProperty("carType","SUV");
        params.setProperty("licensePlate","");
        Throwable exception2 = assertThrows(IOException.class, () -> {
                instance.checkParameters(params);
        });
        assertEquals("Parameters are not valid, see log for more details", exception2.getMessage());
    }
    
        /**
     * Test of execute method, of class RegisterCarCommand.
     */
    @Test
    public void testExecute() {
        Properties params = new Properties();
        Properties params2 = new Properties();
        RealParkingOffice office = new RealParkingOffice();
        RegisterCustomerCommand instance = new RegisterCustomerCommand(office);
        RegisterCarCommand instance2 = new RegisterCarCommand(office);
        
        params.setProperty("firstname", "Jessica");
        params.setProperty("lastname", "Smith");
        params.setProperty("phonenumber", "1 385 294 7164");
        params.setProperty("streetaddress1", "16 Main St");
        params.setProperty("city", "Denver");
        params.setProperty("state", "CO");
        params.setProperty("zip", "35276");
        
        String id = instance.execute(params);
        params2.setProperty("customer",id);
        params2.setProperty("cartype","SUV");
        params2.setProperty("license","D3GS 43FD");
        
        String expResult1 = "P1001"; //Running test file by itself
        String expResult2 = "P1010"; //Running all test files
        String result = instance2.execute(params2);
        System.out.println("HERE: " + result);
        assertTrue(expResult1.equals(result)||expResult2.equals(result));
    }
    
    /**
     * Test of getCommandName method, of class RegisterCarCommand.
     */
    @Test
    public void testGetCommandName() {
        RealParkingOffice office = new RealParkingOffice();
        RegisterCarCommand instance = new RegisterCarCommand(office);
        String expResult = "CAR";
        String result = instance.getCommandName();
        assertEquals(expResult, result);
    }
}
