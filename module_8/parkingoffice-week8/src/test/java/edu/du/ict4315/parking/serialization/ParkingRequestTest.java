/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class ParkingRequestTest {
    
    public ParkingRequestTest() {
    }

    /**
     * Test of toString method, of class ParkingRequest.
     */
    @Test
    public void testToString() {
        String str = "{\"command\":\"CUSTOMER\",\"props\":{'firstname':'Jane','lastname':'Smith','phonenumber':'5293847592'}}";
        ParkingRequest json = new ParkingRequest(str);
        String s = json.toString();
        System.out.println(s);
        assertTrue(s.contains("'firstname':\"Jane\""));
        assertTrue(s.contains("'lastname':\"Smith\""));
        assertTrue(s.contains("'phonenumber':\"5293847592\""));
        assertTrue(s.contains("'command':\"CUSTOMER\""));
    }

    
}
