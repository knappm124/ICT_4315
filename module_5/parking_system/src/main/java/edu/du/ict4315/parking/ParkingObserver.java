/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking;

/**
 *
 * @author melissa
 */
public class ParkingObserver {
    TransactionManager manager;
    
    public void update(ParkingEvent event, String parkingLotId){
        if(event.getTimeOut() == null){
            manager.park(event.getTimeIn(), manager.getPermit(event.getPermitId()), manager.getParkingLot(parkingLotId));
        } else {
            manager.park(event.getTimeOut(), manager.getPermit(event.getPermitId()), manager.getParkingLot(parkingLotId));
        }
    }
}
