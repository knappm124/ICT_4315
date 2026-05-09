/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.observer;

import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.TransactionManager;

/**
 *
 * @author melissa
 */
public class ParkingObserver {
    TransactionManager manager;
    
    public ParkingObserver(TransactionManager manager){
        this.manager = manager;
    }
    
    public ParkingObserver(RealParkingOffice rpo){
        this.manager = rpo.getTransactionManager();
    }
    
    public void update(ParkingEvent event){
            manager.park(event);
    }
    
    public TransactionManager getTransactionManager() {
        return manager;
    }
   
    public void setTransactionManager(TransactionManager manager) {
        this.manager = manager;
    }
}
