/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 *
 * @author melyg
 */
public class ParkingLotModule implements Module{

    @Override
    public void configure(Binder binder) {
        binder.bind(ParkingLot.class).to(ParkingLotChargeOnEntry.class);
    }
 
}
