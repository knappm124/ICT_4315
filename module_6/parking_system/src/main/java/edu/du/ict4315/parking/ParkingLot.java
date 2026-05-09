 ////////////////////
// This class represents the Parking Lot
// File: ParkingLot.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.charges.strategy.BaseStrategyCharge;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingLot {

    private String id;
    private String name;
    private Address address;
    private Money baseRate = Money.of(5.00);
    private BaseStrategyCharge strategy = new BaseStrategyCharge();
    private ArrayList<ParkingObserver> observers = new ArrayList<>();
    private ParkingEvent event;

    public ParkingLot(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ParkingLot(String id, String name, Address address, Money baseRate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.baseRate = baseRate;
    }

    public Money getParkingCharges(ParkingPermit p, LocalDateTime in) {
        return baseRate;
    }

    public Money getBaseRate() {
        return baseRate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id);
        sb.append("\n");
        sb.append(name);
        sb.append("\n");
        sb.append(address);

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ParkingEvent getParkingEvent(){
        return event;
    }
    
    public void setParkingEvent(ParkingEvent event){
        this.event = event;
    }
    
    public ParkingChargeStrategy getParkingChargeStrategy() {
        return strategy;
    }

    public void enter(LocalDateTime time, String permitId) {
        event = new ParkingEvent(time, permitId, this);
        notifyObservers();
    }

    // Method for permit-required-on-exit lot
    public void exit(LocalDateTime time, String permitId) {
        event = new ParkingEvent(time, permitId, this);
        notifyObservers();
    }
    
    public ArrayList<ParkingObserver> addObserver(ParkingObserver observer){
        observers.add(observer);
        return observers;
    }

    public ArrayList<ParkingObserver> removeObserver(ParkingObserver observer){
        observers.remove(observer);
        return observers;
    }
    
    public void notifyObservers(){
        for (ParkingObserver observer : observers){
            observer.update(event);
        }
    }
}
