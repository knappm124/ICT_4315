/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.decorator.CompactCarDiscount;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author melissa
 */
public class CompactDiscountCharge implements ParkingChargeStrategy {

    @Override
    public Money getParkingCharge(Money baseRate, LocalDateTime time, Duration duration, ParkingPermit permit) {
        int days = (int) duration.toDays() + 1;
        Money amount = Money.times(baseRate,days);
        if(permit.getCar().getType() == CarType.COMPACT){
            amount = Money.times(amount,0.8);
        }
        return amount;
    }

    public String getStrategyName() {
        return "CompactDiscount";
    }
    
}
