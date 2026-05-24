/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author melyg
 */
public class BaseStrategyCharge implements ParkingChargeStrategy {

    @Override
    public Money getParkingCharge(Money baseRate, LocalDateTime timeIn, Duration duration, ParkingPermit permit) {
        int days = (int) duration.toDays() + 1;
        return Money.times(baseRate,days);
    }

    public String getStrategyName() {
        return "BaseStrategy";
    }
}
