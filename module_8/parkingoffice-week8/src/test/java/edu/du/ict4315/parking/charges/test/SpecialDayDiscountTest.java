/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import edu.du.ict4315.parking.charges.decorator.SpecialDayDiscount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class SpecialDayDiscountTest {
    
    public SpecialDayDiscountTest() {
    }

    /**
     * Test of getParkingCharge method, of class SpecialDayDiscount.
     */
    @Test
    public void testGetParkingCharge() {
        //Set up parking office, 1 car, and 1 customer
        RealParkingOffice office = new RealParkingOffice();
        Customer cust = new Customer();
        Car c = new Car(CarType.COMPACT,"3RG4XD4",cust);
        String permitId = office.register(c);
        
        //Create variables for timeIn and timeOut
        LocalDateTime timeIn = LocalDateTime.of(2026,5,4,0,0);
        LocalDateTime timeOut = LocalDateTime.of(2026,5,6,0,0);
        LocalDateTime timeIn2 = LocalDateTime.of(2026,12,24,0,0);
        LocalDateTime timeOut2 = LocalDateTime.of(2026,12,26,0,0);
        
        
        //Get parking lot
        ParkingLot lot = office.getParkingLot("108");
        
        //Get parking permit from permit id
        ParkingPermit permit = office.getParkingPermit(permitId);
        
        //Create calculator and get parking charge
        SpecialDayDiscount calc = new SpecialDayDiscount(new FlatRateCalculator());
        
        //Parking time was 2 days, at $2 per day, that would be $4 without special days
        Money result = calc.getParkingCharge(timeIn,timeOut,permit, lot);
        Money expResult = Money.of(4.0);
        assertEquals(result.doubleValue(),expResult.doubleValue());
        
        //With 1 special day, it would be $2 + ($2 * 0.8), which is $3.60
        Money result2 = calc.getParkingCharge(timeIn2,timeOut2,permit,lot);
        Money expResult2 = Money.of(3.6);
        assertEquals(result2.doubleValue(),expResult2.doubleValue());
        
    }

    /**
     * Test of addSpecialDay method, of class SpecialDayDiscount.
     */
    @Test
    public void testAddSpecialDay() {
        //Set up parking office, 1 car, and 1 customer
        RealParkingOffice office = new RealParkingOffice();
        Customer cust = new Customer();
        Car c = new Car(CarType.COMPACT,"3RG4XD4",cust);
        String permitId = office.register(c);
        
        //Create variables for timeIn and timeOut
        LocalDateTime timeIn = LocalDateTime.of(2026,7,4,0,0);
        LocalDateTime timeOut = LocalDateTime.of(2026,7,6,0,0);
        LocalDateTime newSpecialDay = LocalDateTime.of(2026,7,5,0,0);
        
        //Get parking lot
        ParkingLot lot = office.getParkingLot("108");
        
        //Get parking permit from permit id
        ParkingPermit permit = office.getParkingPermit(permitId);
        
        //Create calculator and get parking charge
        SpecialDayDiscount calc = new SpecialDayDiscount(new FlatRateCalculator());
        
        //Parking time was 2 days, at $2 per hour, that would be $3.6 with 1 special days
        Money result = calc.getParkingCharge(timeIn,timeOut,permit,lot);
        Money expResult = Money.of(3.6);
        assertEquals(result.doubleValue(),expResult.doubleValue());
        
        //Add new special day and retest
        calc.addSpecialDay(newSpecialDay);
        Money result2 = calc.getParkingCharge(timeIn,timeOut,permit,lot);
        Money expResult2 = Money.of(3.2);
        assertEquals(result2.doubleValue(),expResult2.doubleValue());
    }
    
}
