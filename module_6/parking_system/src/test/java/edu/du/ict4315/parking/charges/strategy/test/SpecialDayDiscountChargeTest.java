/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.LotType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import edu.du.ict4315.parking.charges.decorator.SpecialDayDiscount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class SpecialDayDiscountChargeTest {
    
    public SpecialDayDiscountChargeTest() {
    }

    /**
     * Test of parkingCharge method, of class SpecialDayDiscountCharge.
     */
    @Test
    public void testParkingCharge() {
        //Set up customer and parking lot
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
        Money baseRate = Money.of(1.00);
        ParkingLot lot = new ParkingLot("1","Test Lot",lotAddress,baseRate, LotType.DAILY);
        lot.setParkingChargeStrategy("SpecialDay");
        Customer tempCustomer = new Customer("CUST-1","Jessica","Jones","6278549652",customerAddress);
        
        //Setup 2 cars, one that is an SUV and one that is a Compact
        Car compactCar = new Car(CarType.COMPACT, "FE8D51T",tempCustomer);
        ParkingPermit permit = new ParkingPermit("1",compactCar,LocalDateTime.now().plusYears(3));
        
        //Create variables for timeIn and timeOut
        LocalDateTime timeIn = LocalDateTime.of(2026,5,4,0,0);
        LocalDateTime timeOut = LocalDateTime.of(2026,5,6,0,0);
        LocalDateTime timeIn2 = LocalDateTime.of(2026,12,24,0,0);
        LocalDateTime timeOut2 = LocalDateTime.of(2026,12,26,0,0);
        
        //Parking time was 2 days, at $1 per day, that would be $2 without special days
        Money result = lot.getParkingCharges(permit,timeIn,timeOut);
        Money expResult = Money.of(2.0);
        assertEquals(result.doubleValue(),expResult.doubleValue());
        
        //With 1 special day, it would be $1.8
        Money result2 = lot.getParkingCharges(permit,timeIn2,timeOut2);
        Money expResult2 = Money.of(1.8);
        assertEquals(result2.doubleValue(),expResult2.doubleValue());
    }  
}