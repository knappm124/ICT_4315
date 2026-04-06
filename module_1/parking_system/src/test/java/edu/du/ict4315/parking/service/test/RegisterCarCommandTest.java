/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.service.test;

import edu.du.ict4315.parking.service.RegisterCarCommand;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Melissa Knapp <knappm124@gmail.com>
 */
public class RegisterCarCommandTest {
    
    public RegisterCarCommandTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of execute method, of class RegisterCarCommand.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        Properties params = null;
        RegisterCarCommand instance = new RegisterCarCommand();
        String expResult = "";
        String result = instance.execute(params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommandName method, of class RegisterCarCommand.
     */
    @Test
    public void testGetCommandName() {
        System.out.println("getCommandName");
        RegisterCarCommand instance = new RegisterCarCommand();
        String expResult = "";
        String result = instance.getCommandName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDisplayName method, of class RegisterCarCommand.
     */
    @Test
    public void testGetDisplayName() {
        System.out.println("getDisplayName");
        RegisterCarCommand instance = new RegisterCarCommand();
        String expResult = "";
        String result = instance.getDisplayName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
