/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingEvent;
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
 * @author melyg
 */
public class TransactionManagerTest {
    

    /**
     * Test of park method, of class TransactionManager.
     */
    @Test
    public void testPark() {
        RealParkingOffice rpo = new RealParkingOffice();
        TransactionManager tm = new TransactionManager(rpo);
        ParkingLot l = rpo.getParkingLot("W");
        Customer cust = new Customer();
        Car ca = new Car(CarType.COMPACT,"F2S32FB",cust);
        String p = rpo.register(ca);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingEvent pe = new ParkingEvent(ldt,p,l);
        ParkingTransaction result = tm.park(pe);
        ParkingTransaction expResult = new ParkingTransaction(ldt,rpo.getParkingPermit(p),l,Money.of(5.00));
        assertEquals(result.getDate(),expResult.getDate());
        assertEquals(result.getPermit(),expResult.getPermit());
        assertEquals(result.getParkingLot(),expResult.getParkingLot());
        assertEquals(result.getChargedAmount(),expResult.getChargedAmount());
    }

    /**
     * Test of getParkingCharges method, of class TransactionManager.
     */
    @Test
    public void testGetParkingCharges_Customer() {
        RealParkingOffice rpo = new RealParkingOffice();
        TransactionManager tm = new TransactionManager(rpo);
        ParkingLot l = rpo.getParkingLot("W");
        Customer cust = new Customer();
        Car ca = new Car(CarType.COMPACT,"F2S32FB",cust);
        String p = rpo.register(ca);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingEvent pe = new ParkingEvent(ldt,p,l);
        tm.park(pe);
        Money result = tm.getParkingCharges(cust);
        Money expResult = Money.of(5.00);
        assertEquals(result,expResult); 
    }

    /**
     * Test of getParkingCharges method, of class TransactionManager.
     */
    @Test
    public void testGetParkingCharges_ParkingPermit() {
        RealParkingOffice rpo = new RealParkingOffice();
        TransactionManager tm = new TransactionManager(rpo);
        ParkingLot l = rpo.getParkingLot("W");
        Customer cust = new Customer();
        Car ca = new Car(CarType.COMPACT,"F2S32FB",cust);
        String p = rpo.register(ca);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingEvent pe = new ParkingEvent(ldt,p,l);
        tm.park(pe);
        ParkingPermit permit = rpo.getParkingPermit(p);
        Money result = tm.getParkingCharges(permit);
        Money expResult = Money.of(5.00);
        assertEquals(result,expResult);
    }
    
}
