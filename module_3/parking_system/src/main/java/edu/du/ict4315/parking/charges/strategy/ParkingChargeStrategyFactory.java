/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;

/**
 *
 * @author melissa
 */
public class ParkingChargeStrategyFactory {
    
    private static final Map<String,Supplier<ParkingChargeStrategy>> strategyMap = new HashMap();
    
    static {
        strategyMap.put("BaseStrategy", BaseStrategyCharge::new);
        strategyMap.put("WeekendDiscount",WeekendDiscountCharge::new);
        strategyMap.put("SpecialDayDiscount",SpecialDayDiscountCharge::new);
    }
    
    
    public ParkingChargeStrategy createStrategy(String type){
        if(strategyMap.get(type) != null){
            return strategyMap.get(type).get();
        } else {
            throw new IllegalArgumentException(type + " parking strategy is not a valid strategy");
        }
    }
    
    
}
