/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.parking.charges.factory.FactoryDesignatedDayNineToSix;
import edu.du.ict4315.parking.charges.factory.FactoryFavorLargeCarsLateEntry;
import edu.du.ict4315.parking.charges.factory.FactoryFavorSmallCarsEarlyIn;
import edu.du.ict4315.parking.charges.factory.FactoryFlatRate;
// import edu.du.ict4315.parking.charges.factory.FactoryNoWeekendCharge;
import edu.du.ict4315.parking.charges.factory.FactoryOriginalAlgorithm;
import edu.du.ict4315.parking.charges.factory.FactoryParkingCharges;
// import edu.du.ict4315.parking.charges.factory.FactoryPrimeTimeSurchargeNoWeekendCharge;
import edu.du.ict4315.parking.charges.strategy.DesignatedDayNineToSix;
import edu.du.ict4315.parking.charges.strategy.FavorLargeCarsLateEntry;
import edu.du.ict4315.parking.charges.strategy.FavorSmallCarsEarlyIn;
import edu.du.ict4315.parking.charges.strategy.FlatRate;
import edu.du.ict4315.parking.charges.strategy.NoWeekendCharge;
import edu.du.ict4315.parking.charges.strategy.OriginalAlgorithm;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.PrimeTimeSurchargeNoWeekendCharge;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author michael
 */
public class ParkingChargesFactoryTest {

    @Test
    void testStrategy() {
        FactoryParkingCharges[] factoryNames = {
            new FactoryDesignatedDayNineToSix(),
            new FactoryFavorLargeCarsLateEntry(),
            new FactoryFavorSmallCarsEarlyIn(),
            new FactoryFlatRate(),
//            new FactoryNoWeekendCharge(),
            new FactoryOriginalAlgorithm(),
//            new FactoryPrimeTimeSurchargeNoWeekendCharge(),
        };
        ParkingChargeStrategy[] strategies = {
            new DesignatedDayNineToSix(),
            new FavorLargeCarsLateEntry(),
            new FavorSmallCarsEarlyIn(),
            new FlatRate(),
  //           new NoWeekendCharge(),
            new OriginalAlgorithm(),
  //          new PrimeTimeSurchargeNoWeekendCharge(),
        };

        for (int i = 0; i < factoryNames.length; i++) {
            assertEquals(factoryNames[i].getStrategy().toString(), strategies[i].toString());
        }
    }
}
