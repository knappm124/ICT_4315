/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.charges.strategy.test;

import edu.du.ict4315.parking.charges.strategy.BaseStrategyCharge;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategyFactory;
import edu.du.ict4315.parking.charges.strategy.SpecialDayDiscountCharge;
import edu.du.ict4315.parking.charges.strategy.WeekendDiscountCharge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class ParkingChargeStrategyFactoryTest {
    
    public ParkingChargeStrategyFactoryTest() {
    }

    /**
     * Test of createStrategy method, of class ParkingChargeStrategyFactory.
     */
    @Test
    public void testCreateStrategy() {

        ParkingChargeStrategyFactory instance = new ParkingChargeStrategyFactory();
        String expResult = new BaseStrategyCharge().getStrategyName();
        ParkingChargeStrategy result = instance.createStrategy("BaseStrategy");
        
        assertEquals(expResult, result.getStrategyName());
        
        expResult = new SpecialDayDiscountCharge().getStrategyName();
        
        ParkingChargeStrategy result2 = instance.createStrategy("SpecialDayDiscount");
        assertEquals(expResult, result2.getStrategyName());
        
        expResult = new WeekendDiscountCharge().getStrategyName();
        
        ParkingChargeStrategy result3 = instance.createStrategy("WeekendDiscount");
        assertEquals(expResult, result3.getStrategyName());
    }
    
}
