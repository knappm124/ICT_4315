/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.charges.strategy;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import java.util.HashMap;

/**
 *
 * @author melyg
 */
public class BaseStratgeyCharge implements ParkingChargeStrategy {

    @Override
    public Money parkingCharge(ParkingLot lot, HashMap<String,Boolean> days, Integer timeParked, ParkingPermit permit) {
        //Get parking lots hourly rate, set total charge at $0, and find the remainder when dividing time parked by 24
        Money rate = lot.getBaseRate();
        Money totalCharge = Money.of(0.00);
        Integer hours = timeParked % 24;

        //Calculate daily charge if parked for more than a day
        if (timeParked > 24) {
            //Calculate daily charges if parked for more than 1 day
            for (int i = 1; i < days.size(); i++) {
                Money dailyCharge = Money.times(rate, 24);
                totalCharge = Money.add(totalCharge, dailyCharge);
            }
        } 
        
        Money hourlyCharge = Money.times(rate, hours);
        totalCharge = Money.add(totalCharge, hourlyCharge);

        //Check if car attached to permit is a compact and take 20% off
        if (permit.getCar().getType() == CarType.COMPACT) {
            totalCharge = Money.times(totalCharge, 0.8);
        }
        return totalCharge;
    }
}
