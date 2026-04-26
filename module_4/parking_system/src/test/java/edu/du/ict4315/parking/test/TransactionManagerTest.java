/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.TransactionManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class TransactionManagerTest {
    
    public TransactionManagerTest() {
    }

    /**
     * Test of park method, of class TransactionManager.
     */
    @Test
    public void testPark() {
        LocalDateTime d = LocalDateTime.of(2026,04,22,05,25);
        LocalDateTime e = LocalDateTime.of(2028,01,01,00,00);
        
        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica","Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV,"35K2JE43",tempCustomer);
        ParkingPermit p = new ParkingPermit("1528",newCar,e);
        ParkingLot l = new ParkingLot("1","Test Lot",lotAddress,Money.of(1.00));
        
        //Set up RealParkingOffice
        RealParkingOffice office = new RealParkingOffice();
        office.setParkingOfficeName("Test Office");
        Address officeAddress = new Address.Builder()
                .withStreetAddress1("789 University St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        office.setParkingOfficeAddress(officeAddress);
        office.addParkingLot(l);
        office.register(tempCustomer);
        office.register(newCar);
        
        TransactionManager instance = new TransactionManager(office);
        ParkingTransaction expResult = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        ParkingTransaction result = instance.park(d, p, l);
        assertEquals(expResult.getChargedAmount(), result.getChargedAmount());
        assertEquals(expResult.getDate(), result.getDate());
        assertEquals(expResult.getPermit(), result.getPermit());
        assertEquals(expResult.getParkingLot(), result.getParkingLot());
    }
    
}
