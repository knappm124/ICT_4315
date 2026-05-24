/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author melyg
 */
public class CompactCarDiscount extends ParkingChargeCalculator {
    private ParkingChargeCalculator calc;
    
    
    public CompactCarDiscount(ParkingChargeCalculator calc) {
        this.calc = calc;
    }
    
     @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingPermit permit, ParkingLot lot){
        Money amount = calc.getParkingCharge(timeIn,timeOut,permit,lot);
        CarType type = permit.getCar().getType();
        if(type == CarType.COMPACT){
            amount = Money.times(amount,0.8);
        }
        return amount;
    }
    
    @Override
    public List<String> getDescription(){
        List<String> string = super.getDescription();
        string.add("Compact car discount");
        return string;
    }
}

