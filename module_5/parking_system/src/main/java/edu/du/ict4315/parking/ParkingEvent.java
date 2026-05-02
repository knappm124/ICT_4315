/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking;

import java.time.LocalDateTime;

/**
 *
 * @author melissa
 */
public class ParkingEvent {
    private LocalDateTime time;
    private String permitId;
    private ParkingLot lot;
    
    public ParkingEvent(LocalDateTime time, String permitId, ParkingLot lot){
        this.time = time;
        this.permitId = permitId;
        this.lot = lot;
    }
    
    public String getPermitId() {
        return permitId;
    }
    
    public void setPermitId(String permitId){
        this.permitId = permitId;
    }
    
    public LocalDateTime getTime(){
        return time;
    }
    
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    
    public ParkingLot getParkingLot(){
        return lot;
    }
    
    public void setParkingLot(ParkingLot lot){
        this.lot = lot;
    }
}

