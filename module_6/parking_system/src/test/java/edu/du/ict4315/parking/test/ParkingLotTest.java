/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.observer.ParkingEvent;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.observer.ParkingObserver;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.TransactionManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class ParkingLotTest {
   

    /**
     * Test of getParkingCharges method, of class ParkingLot.
     */
    @Test
    public void testGetParkingCharges() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        LocalDateTime timeOut = LocalDateTime.now();
        LocalDateTime timeIn = timeOut.minusHours(5);
        Customer cust = new Customer();
        Car c = new Car(CarType.SUV,"EK23G2D",cust);
        ParkingPermit p = new ParkingPermit("P1001",c,timeOut.plusYears(3));
        Money result = l.getParkingCharges(p,timeIn,timeOut);
        Money expResult = Money.of(25.00);
        assertEquals(result,expResult);
    }

    

    /**
     * Test of enter method, of class ParkingLot.
     */
    @Test
    public void testEnter() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        Customer cust = new Customer();
        Car ca = new Car(CarType.SUV,"K35FBW3",cust);
        String permitId = rpo.register(ca);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingObserver observer = new ParkingObserver(rpo);
        l.addObserver(observer);
        l.enter(ldt, permitId);
        TransactionManager tm = rpo.getTransactionManager();
        Money result = tm.getParkingCharges(cust);
        assertEquals(result,Money.of(5.00));
    }

    /**
     * Test of exit method, of class ParkingLot.
     */
    @Test
    public void testExit() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        Customer cust = new Customer();
        Car ca = new Car(CarType.SUV,"K35FBW3",cust);
        String permitId = rpo.register(ca);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingObserver observer = new ParkingObserver(rpo);
        l.addObserver(observer);
        l.exit(ldt, permitId);
        TransactionManager tm = rpo.getTransactionManager();
        Money result = tm.getParkingCharges(cust);
        assertEquals(result,Money.of(5.00));
    }

    /**
     * Test of addObserver method, of class ParkingLot.
     */
    @Test
    public void testAddObserver() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        TransactionManager tm = rpo.getTransactionManager();
        ParkingObserver observer = new ParkingObserver(tm);
        ArrayList<ParkingObserver> list = l.addObserver(observer);
        boolean result = list.contains(observer);
        assertTrue(result);
    }

    /**
     * Test of removeObserver method, of class ParkingLot.
     */
    @Test
    public void testRemoveObserver() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        TransactionManager tm = rpo.getTransactionManager();
        ParkingObserver observer = new ParkingObserver(tm);
        ArrayList<ParkingObserver> list = l.addObserver(observer);
        boolean result = list.contains(observer);
        assertTrue(result);
        list = l.removeObserver(observer);
        result = list.contains(observer);
        assertFalse(result);
    }

    /**
     * Test of notifyObservers method, of class ParkingLot.
     */
    @Test
    public void testNotifyObservers() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingLot l = rpo.getParkingLot("W");
        ParkingObserver observer = new ParkingObserver(rpo);
        l.addObserver(observer);
        LocalDateTime ldt = LocalDateTime.now();
        ParkingEvent event = new ParkingEvent(ldt,"P1001",l);
        l.setParkingEvent(event);
        l.notifyObservers();
        TransactionManager tm = rpo.getTransactionManager();
        List<ParkingTransaction> result = tm.getTransactions();
        for(ParkingTransaction t : result){
            assertEquals(t.getChargedAmount(),Money.of(5.00));
            assertEquals(t.getDate(),ldt);
        }
    }
    
}
