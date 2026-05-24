/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class ParkingResponseTest {
    
    public ParkingResponseTest() {
    }

    /**
     * Test of toString method, of class ParkingResponse.
     */
    @Test
    public void testToString() {
        String str = "{'statuscode':400,'message':'Bad Request'}";
        ParkingResponse response = new ParkingResponse(str);
        String s = response.toString();
        assertEquals(s,"{'statuscode':400,'message':'Bad Request'}");
    }

    
}
