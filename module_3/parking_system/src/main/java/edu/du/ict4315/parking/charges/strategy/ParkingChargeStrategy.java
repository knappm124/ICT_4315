/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.util.HashMap;

/**
 *
 * @author melyg
 */
public interface ParkingChargeStrategy {
    public Money parkingCharge(ParkingLot lot, HashMap<String,Boolean> days, Integer timeParked, ParkingPermit permit);
}
