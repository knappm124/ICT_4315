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

/**
 *
 * @author melyg
 */
public class CompactCarDiscount extends ParkingChargeDecorator {

    public CompactCarDiscount(FlatRateCalculator rate) {
        super(rate);
    }
    
     @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingLot lot, ParkingPermit permit){
        Money amount = super.getParkingCharge(timeIn,timeOut,lot,permit);
        CarType type = permit.getCar().getType();
        if(type == CarType.COMPACT){
            amount = Money.times(amount,0.8);
        }
        return amount;
    }
}
