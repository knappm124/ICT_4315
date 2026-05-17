/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.charges.decorator;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.LotType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author melyg
 */
public class FlatRateCalculator implements ParkingChargeCalculator {
    
    @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingLot lot, ParkingPermit permit){
        Money rate = lot.getBaseRate();
        if(lot.getType()==LotType.HOURLY){
            long days = timeIn.until(timeOut, ChronoUnit.DAYS);
            if(days == 0){
                return rate;
            } else {
                return Money.times(rate, days);
            }
        } else {
            long hours = timeIn.until(timeOut, ChronoUnit.HOURS);
            return Money.times(rate,hours);
        }
    }
}
