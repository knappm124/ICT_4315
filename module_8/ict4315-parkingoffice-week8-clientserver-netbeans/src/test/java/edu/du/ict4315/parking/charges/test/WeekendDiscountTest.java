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
import edu.du.ict4315.parking.charges.decorator.WeekendDiscount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class WeekendDiscountTest {
    
    public WeekendDiscountTest() {
    }

    /**
     * Test of getParkingCharge method, of class WeekendDiscount.
     */
    @Test
    public void testGetParkingCharge() {
        //Set up parking office, 1 car, and 1 customer
        RealParkingOffice office = new RealParkingOffice();
        Customer cust = new Customer();
        Car c = new Car(CarType.COMPACT,"3RG4XD4",cust);
        String permitId = office.register(c);
        
        //Create variables for timeIn and timeOut
        LocalDateTime timeIn = LocalDateTime.of(2026,5,7,0,0);
        LocalDateTime timeOut = LocalDateTime.of(2026,5,9,0,0);
        
        //Get parking lot
        ParkingLot lot = office.getParkingLot("321");
        
        //Get parking permit from permit id
        ParkingPermit permit = office.getParkingPermit(permitId);
        
        //Create calculator and get parking charge
        WeekendDiscount calc = new WeekendDiscount(new FlatRateCalculator());
        
        //Parking time was 2 days, at $8 per day, that would be $16, with 20% off for 1 weekend day it's $14.40
        Money result = calc.getParkingCharge(timeIn,timeOut,permit,lot);
        Money expResult = Money.of(14.4);
        assertEquals(result.doubleValue(),expResult.doubleValue());
    }   
}
