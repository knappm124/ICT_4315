/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.util.HashMap;

/**
 *
 * @author melyg
 */
public class SpecialDayDiscountCharge implements ParkingChargeStrategy {
    @Override
    public Money parkingCharge(ParkingLot lot, HashMap<String,Boolean> days, Integer timeParked, ParkingPermit permit) {
        //Get parking lots hourly rate, set total charge at $0, and find the remainder when dividing time parked by 24
        Money rate = lot.getBaseRate();
        Money totalCharge = Money.of(0.00);
        Integer hours = timeParked % 24;
        
        //Calculate daily charge if parked for more than a day
        if (timeParked > 24) {
            int specialDays = 0;
            int normalDays = 0;
            //Check if it's a special day and count each
            for (Boolean value : days.values()) {
                if (value) {
                    specialDays += 1;
                } else {
                    normalDays += 1;
                }
            }
            //Check if time parked is divisible by 24 and remove 1 day from either variable
            if (timeParked % 24 != 0) {
                if (normalDays >= 1) {
                    normalDays -= 1;
                } else if (normalDays == 0) {
                    specialDays -= 1;
                }
            }
            //
            Money weekendCharge = Money.times(rate, 24*specialDays);
            weekendCharge = Money.times(weekendCharge, 0.8);
            Money weekdayCharge = Money.times(rate, 24*normalDays);
            totalCharge = Money.add(weekdayCharge, weekendCharge);
        } else {
            //If parked for a day or less, check if it's a special day and take 20% off hourly rate
            if(days.containsValue(true)){
                rate = Money.times(rate, 0.8);
            }
        }
        
        Money hourlyCharge = Money.times(rate, hours);
        totalCharge = Money.add(totalCharge,hourlyCharge);
        
        //Check if car attached to permit is a compact and take 20% off
        if(permit.getCar().getType() == CarType.COMPACT) {
            totalCharge = Money.times(totalCharge, 0.8);
        }
        return totalCharge;
    }  

    @Override
    public String getStrategyName() {
        return "SpecialDayDiscount";
    }
}
