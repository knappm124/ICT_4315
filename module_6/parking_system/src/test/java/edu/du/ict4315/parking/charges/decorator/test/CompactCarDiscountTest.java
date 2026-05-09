/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.charges.decorator.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.decorator.CompactCarDiscount;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class CompactCarDiscountTest {
    
    public CompactCarDiscountTest() {
    }

    /**
     * Test of getParkingCharge method, of class CompactCarDiscount.
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
        
        //Get parking lot
        ParkingLot lot = office.getParkingLot("301");
        
        //Get parking permit from permit id
        ParkingPermit permit = office.getParkingPermit(permitId);
        
        //Create calculator and get parking charge
        CompactCarDiscount calc = new CompactCarDiscount(new FlatRateCalculator());
        
        //Parking time was 2 days, at $8 per day, that would be $16, with 20% off it's $12.80
        Money result = calc.getParkingCharge(timeIn,timeOut,lot,permit);
        Money expResult = Money.of(12.8);
        assertEquals(result.doubleValue(),expResult.doubleValue());
    }
    
}
