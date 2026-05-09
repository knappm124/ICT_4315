/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 *
 * @author melyg
 */
public class WeekendDiscount extends ParkingChargeDecorator {

    public WeekendDiscount(FlatRateCalculator rate) {
        super(rate);
    }

    @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingLot lot, ParkingPermit permit) {
        Money amount = super.getParkingCharge(timeIn, timeOut, lot, permit);
        int day1 = timeIn.getDayOfYear();
        int day2 = timeOut.getDayOfYear();
        if (day1 == day2) {
            switch (timeIn.getDayOfWeek()) {
                case DayOfWeek.SATURDAY ->
                    amount = Money.times(amount, 0.8);
                case DayOfWeek.SUNDAY ->
                    amount = Money.times(amount, 0.8);
                default -> {
                }
            }
        } else {
            LocalDateTime counter = timeIn;
            int between = day2 - day1;
            int weekendDays = 0;
            for (int i = 0; i <= between; i++) {
                switch (counter.getDayOfWeek()) {
                    case DayOfWeek.SATURDAY ->
                        weekendDays += 1;
                    case DayOfWeek.SUNDAY ->
                        weekendDays += 1;
                }
                counter = counter.plusDays(1);
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
}
