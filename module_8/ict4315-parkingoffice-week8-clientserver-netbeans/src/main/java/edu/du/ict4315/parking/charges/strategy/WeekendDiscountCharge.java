/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.charges.decorator.FlatRateCalculator;
import edu.du.ict4315.parking.charges.decorator.WeekendDiscount;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author melyg
 */
public class WeekendDiscountCharge implements ParkingChargeStrategy {

    @Override
    public Money getParkingCharge(Money baseRate, LocalDateTime timeIn, Duration duration, ParkingPermit p) {
        Money amount = Money.times(baseRate,duration.toDays()+1);
        LocalDateTime timeOut = timeIn.plus(duration);
        int day1 = timeIn.getDayOfYear();
        int day2 = timeOut.getDayOfYear();
        String date = timeIn.getDayOfWeek().toString();
        if (day1 == day2) {
            switch (date) {
                case "SATURDAY":
                    amount = Money.times(amount, 0.8);
                    break;
                case "SUNDAY":
                    amount = Money.times(amount, 0.8);
                    break;
                
            }
        } else {
            LocalDateTime counter = timeIn;
            int between = day2 - day1;
            int weekendDays = 0;
            for (int i = 0; i <= between; i++) {
                switch (date) {
                    case "SATURDAY" ->
                        weekendDays += 1;
                    case "SUNDAY" ->
                        weekendDays += 1;
                }
                counter = counter.plusDays(1);
                date = counter.getDayOfWeek().toString();
            }
            if (weekendDays > between) {
                weekendDays = between;
            }
            double percentWeekend = (double) weekendDays / (double) between;
            if (percentWeekend != 0) {
                double weekendDiscount = percentWeekend * 0.2;
                Money discount = Money.times(amount, weekendDiscount);  
                amount = Money.subtract(amount,discount);
            }
        }
        return amount;
    }

    public String getStrategyName() {
        return "WeekendDiscount";
    }
}
