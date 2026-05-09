/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.decorator;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.time.LocalDateTime;

/**
 *
 * @author melyg
 */
public abstract class ParkingChargeDecorator implements ParkingChargeCalculator {
    private ParkingChargeCalculator rate;
    
    public ParkingChargeDecorator(FlatRateCalculator rate){
        this.rate = rate;
    }
    
    @Override
    public Money getParkingCharge(LocalDateTime timeIn, LocalDateTime timeOut, ParkingLot lot, ParkingPermit permit){
        return rate.getParkingCharge(timeIn, timeOut, lot, permit);
    }
}
