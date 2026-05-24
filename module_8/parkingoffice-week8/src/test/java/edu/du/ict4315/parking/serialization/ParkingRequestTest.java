/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.serialization;

import com.google.gson.JsonObject;
import java.util.Properties;
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
        Properties prop = new Properties();
        prop.setProperty("firstname","Jane");
        prop.setProperty("lastname","Smith");
        prop.setProperty("phonenumber","5293847592");
        ParkingRequest json = new ParkingRequest("CUSTOMER",prop);
        String s = json.toString();
        assertTrue(s.contains("'firstname':'Jane'"));
        assertTrue(s.contains("'lastname':'Smith'"));
        assertTrue(s.contains("'phonenumber':'5293847592'"));
        assertTrue(s.contains("'command':'CUSTOMER'"));
    }

    /**
     * Test of toJSON method, of class ParkingRequest.
     */
    @Test
    public void testToJSON() {
        Properties prop = new Properties();
        prop.setProperty("firstname","Jane");
        prop.setProperty("lastname","Smith");
        prop.setProperty("phonenumber","5293847592");
        ParkingRequest json = new ParkingRequest("CUSTOMER",prop);
        JsonObject result = json.toJSON();
        String command = result.get("command").getAsString();
        assertEquals(command,"CUSTOMER");
        String props = result.get("props").getAsString();
        assertTrue(props.contains("'firstname':'Jane'"));
        assertTrue(props.contains("'lastname':'Smith'"));
        assertTrue(props.contains("'phonenumber':'5293847592'"));
    }
    
}
