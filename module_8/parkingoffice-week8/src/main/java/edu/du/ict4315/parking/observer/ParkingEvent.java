/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.du.ict4315.parking.observer;

import edu.du.ict4315.parking.ParkingLot;
import java.time.LocalDateTime;

/**
 *
 * @author melissa
 */
public class ParkingEvent {
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private String permitId;
    private ParkingLot lot;
    private Boolean exit;
    
    public ParkingEvent(LocalDateTime timeIn, LocalDateTime timeOut, String permitId, ParkingLot lot, Boolean exit){
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.permitId = permitId;
        this.lot = lot;
        this.exit = exit;
    }
    
    public LocalDateTime getTimeIn(){
        return timeIn;
    }
    
    public void setTimeIn(LocalDateTime timeIn){
        this.timeIn = timeIn;
    }
    
    public LocalDateTime getTimeOut(){
        return timeOut;
    }
    
    public void setTimeOut(LocalDateTime timeOut){
        this.timeOut = timeOut;
    } 
        
    public String getPermitId() {
        return permitId;
    }
    
    public void setPermitId(String permitId){
        this.permitId = permitId;
    }
    
    public ParkingLot getParkingLot(){
        return lot;
    }
    
    public void setParkingLot(ParkingLot lot){
        this.lot = lot;
    }
    
    public Boolean getIsExit(){
        return exit;
    }
    
    public void setIsExit(Boolean exit){
        this.exit = exit;
    }
}

