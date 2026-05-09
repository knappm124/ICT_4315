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
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class CompactDiscountChargeTest {
    
    public CompactDiscountChargeTest() {
    }

    /**
     * Test of parkingCharge method, of class CompactDiscountCharge.
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
        ParkingLot lot = new ParkingLot("1","Test Lot",lotAddress,baseRate,LotType.HOURLY);
        lot.setParkingChargeStrategy("Compact");
        Customer tempCustomer = new Customer("CUST-1","Jessica","Jones","6278549652",customerAddress);
        
        //Set up parking charge variables
        LocalDateTime timeOut = LocalDateTime.now();
        LocalDateTime timeIn = timeOut.minusDays(1);
        
        //Setup 2 cars, one that is an SUV and one that is a Compact
        Car compactCar = new Car(CarType.COMPACT, "FE8D51T",tempCustomer);
        ParkingPermit permit = new ParkingPermit("1",compactCar,LocalDateTime.now().plusYears(3));
        Car suv = new Car(CarType.SUV,"DE5V6H2",tempCustomer);
        ParkingPermit permit2 = new ParkingPermit("2",suv,LocalDateTime.now().plusYears(2));
        
        //Parking lot rate is $1 per hour, 24 hours should be $24 for SUV and 20% less for Compact
        Money expResult = Money.of(19.20);
        Money result = lot.getParkingCharges(permit, timeIn, timeOut);
        assertEquals(expResult.doubleValue(), result.doubleValue());  
        
        Money expResult2 = Money.of(24.00);
        Money result2 = lot.getParkingCharges(permit2, timeIn, timeOut);
        assertEquals(expResult2.doubleValue(), result2.doubleValue());        
    }
}
