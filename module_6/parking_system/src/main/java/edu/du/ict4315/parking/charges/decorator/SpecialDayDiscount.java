/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author melyg
 */
public class SpecialDayDiscount extends ParkingChargeDecorator {

    ArrayList<LocalDateTime> specialDays = new ArrayList<>();
    
    public SpecialDayDiscount(FlatRateCalculator rate) {
        super(rate);
        specialDays.add(LocalDateTime.of(2026,1,1,0,0)); //News Year
        specialDays.add(LocalDateTime.of(2026,6,19,0,0)); //Juneteenth
        specialDays.add(LocalDateTime.of(2026,7,4,0,0)); //4th of July
        specialDays.add(LocalDateTime.of(2026,11,11,0,0)); //Veterans Day
        specialDays.add(LocalDateTime.of(2026,12,25,0,0)); //Christmas
    }
    
    @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingLot lot, ParkingPermit permit){
        Money amount = super.getParkingCharge(timeIn,timeOut,lot,permit);
        int day = timeIn.getDayOfYear();
        int day2 = timeOut.getDayOfYear();
        int between = day2 - day;
        int special = 0;
        for(LocalDateTime time : specialDays){
            if(timeIn.isBefore(time)&&timeOut.isAfter(time)){
                special += 1;
            } else if(time.getDayOfYear() == day || time.getDayOfYear() == day2){
                special += 1;
            }
        }
        if(special > 0){
            double temp = (double) special / (double) between;
            double specialPercent = 0.2 * temp;
            Money discount = Money.times(amount, specialPercent);  
            amount = Money.subtract(amount,discount);
        }
        return amount;
    }
    
    public ArrayList<LocalDateTime> addSpecialDay(LocalDateTime time){
        specialDays.add(time);
        return specialDays;
    }
}


