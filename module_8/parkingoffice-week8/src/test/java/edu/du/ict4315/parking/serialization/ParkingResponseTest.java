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
        ParkingResponse response = new ParkingResponse(400,"Bad Request");
        String s = response.toString();
        assertEquals(s,"{'statuscode':400,'message':'Bad Request'}");
    }

    /**
     * Test of toJSON method, of class ParkingResponse.
     */
    @Test
    public void testToJSON() {
       ParkingResponse response = new ParkingResponse(200,"OK");
       JsonObject json = response.toJSON();
       assertEquals(json.get("statuscode").getAsInt(),200);
       assertEquals(json.get("message").getAsString(),"OK");
    }
    
}
