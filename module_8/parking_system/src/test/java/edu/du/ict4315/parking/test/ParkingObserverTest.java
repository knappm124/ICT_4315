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
import edu.du.ict4315.parking.ParkingObserver;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.TransactionManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class ParkingObserverTest {
   

    /**
     * Test of update method, of class ParkingObserver.
     */
    @Test
    public void testUpdate() {
        RealParkingOffice rpo = new RealParkingOffice();
        ParkingObserver observer = new ParkingObserver(rpo);
        Customer cust = new Customer();
        String permitId = rpo.register(new Car(CarType.SUV,"K3J42FH",cust));
        ParkingLot l = rpo.getParkingLot("W");
        LocalDateTime ldt = LocalDateTime.now();
        ParkingEvent event = new ParkingEvent(ldt, permitId, l);
        observer.update(event);
        TransactionManager tm = observer.getTransactionManager();
        ParkingPermit p = rpo.getParkingPermit(permitId);
        Money result = tm.getParkingCharges(p);
        Money expResult = Money.of(5.00);
        assertEquals(result,expResult);
    }
    
}
