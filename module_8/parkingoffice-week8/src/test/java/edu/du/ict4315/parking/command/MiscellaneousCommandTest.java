/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.command;

import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.service.ParkingService;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class MiscellaneousCommandTest {

    private static RealParkingOffice parkingOffice;
    private static Customer customer;
    private static Car car;
    private static ParkingPermit permit;
    private static ParkingService service;

    @BeforeAll
    public static void setUpClass() {
        parkingOffice = new RealParkingOffice();
        Customer cust = new Customer();
        cust.setLastName("Smith");
        cust.setFirstName("Pat");
        cust.setPhoneNumber("303-555-1212");
        String id = parkingOffice.register(cust);
        customer = parkingOffice.getCustomer(id);
        Car vehicle = new Car();
        vehicle.setLicensePlate("ABC-123");
        vehicle.setType(CarType.COMPACT);
        vehicle.setOwner(customer);
        car = vehicle;
        String permitId = parkingOffice.register(vehicle);
        permit = parkingOffice.getParkingPermit(permitId);
        service = new ParkingService(parkingOffice);
    }

    @Test
    void testListCommands() {
        System.out.println("List commands");
        String[] commands = service.listCommands();
        System.out.println("Commands: " + String.join(", ", commands));
        assertTrue(commands.length > 0);
    }

    @Test
    void testListCust() {
        System.out.println("List Customers");
        String customers = service.performCommand("LISTCUST", new Properties());
        System.out.println("Customers: " + customers);
    }

    @Test
    void testListLots() {
        System.out.println("List Parking Lots");
        String lots = service.performCommand("LISTLOTS", new Properties());
        System.out.println("Parking Lots: " + lots);
    }
}
