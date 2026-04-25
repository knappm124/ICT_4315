/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package parking.charges.strategy;

import edu.du.ict4315.parking.charges.strategy.WeekendDiscountCharge;
import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melyg
 */
public class WeekendDiscountChargeTest {
    
    public WeekendDiscountChargeTest() {
    }

    /**
     * Test of parkingCharge method, of class WeekendDiscountCharge.
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
        ParkingLot lot = new ParkingLot("1","Test Lot",lotAddress,baseRate);
                Customer tempCustomer = new Customer.CustomerBuilder("Jessica","Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        
        //Set up parking charge variables
        HashMap<String, Boolean> days = new HashMap();
        days.put("Monday",false);
        days.put("Sunday",false);
        days.put("Tuesday",true);
        Integer timeParked = 56;
        
        //Setup 2 cars, one that is an SUV and one that is a Compact
        Car compactCar = new Car(CarType.COMPACT, "FE8D51T",tempCustomer);
        ParkingPermit permit = new ParkingPermit("1",compactCar,LocalDateTime.now().plusYears(3));
        Car suv = new Car(CarType.SUV,"DE5V6H2",tempCustomer);
        ParkingPermit permit2 = new ParkingPermit("2",suv,LocalDateTime.now().plusYears(2));
        
        WeekendDiscountCharge instance = new WeekendDiscountCharge();
        
        //Parking lot rate is $1 per hour, 1 of the days is a weekend and  the rest are weekdays, so 1 day is $19.20, 1 day is $24, 
        //and the remainder is $8, which is a total of $51.20 and the Compact car will be 20% less at $40.96
        Money expResult = Money.of(51.20);
        Money result = instance.parkingCharge(lot, days, timeParked, permit2);
        assertEquals(expResult, result);
        
        Money expResult2 = Money.of(40.96);
        Money result2 = instance.parkingCharge(lot, days, timeParked, permit);
        assertEquals(expResult2, result2);
    }
}
