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
    private LocalDateTime in;
    private LocalDateTime out;
    private String permitId;
    
    public ParkingEvent(LocalDateTime in, LocalDateTime out, String permitId){
        this.in = in;
        this.out = out;
        this.permitId = permitId;
    }
    
    public ParkingEvent(LocalDateTime in, String permitId){
        this.in = in;
        this.out = null;
        this.permitId = permitId;
    }
    
    public String getPermitId() {
        return permitId;
    }
    
    public LocalDateTime getTimeIn(){
        return in;
    }
    
    public LocalDateTime getTimeOut(){
        return out;
    }
}
