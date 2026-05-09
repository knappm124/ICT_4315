/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import edu.du.ict4315.parking.charges.decorator.SpecialDayDiscount;
import java.time.LocalDateTime;

/**
 *
 * @author melyg
 */
public class SpecialDayDiscountCharge implements ParkingChargeStrategy {
    @Override
    public Money parkingCharge(ParkingLot lot, LocalDateTime timeIn, LocalDateTime timeOut, ParkingPermit permit) {
        SpecialDayDiscount calc = new SpecialDayDiscount(new FlatRateCalculator());
        return calc.getParkingCharge(timeIn, timeOut, lot, permit);
    }  

    @Override
    public String getStrategyName() {
        return "SpecialDayDiscount";
    }
}
