/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.charges.factory;

import com.google.inject.Binder;
import com.google.inject.Module;


/**
 *
 * @author melyg
 */
public class FactoryParkingChargeModule implements Module {

    @Override
    public void configure(Binder binder) {
       binder.bind(FactoryParkingChargeCalculator.class)
               .to(FactoryDesignatedDayNineToSix.class);
    }

}
