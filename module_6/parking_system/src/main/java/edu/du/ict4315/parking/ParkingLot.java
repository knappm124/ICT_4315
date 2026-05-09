 ////////////////////
// This class represents the Parking Lot
// File: ParkingLot.java
// Author: M. I. Schwartz
////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.parking.observer.ParkingEvent;
import edu.du.ict4315.parking.observer.ParkingObserver;
import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.charges.strategy.BaseStrategyCharge;
import edu.du.ict4315.parking.charges.strategy.CompactDiscountCharge;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategy;
import edu.du.ict4315.parking.charges.strategy.SpecialDayDiscountCharge;
import edu.du.ict4315.parking.charges.strategy.WeekendDiscountCharge;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParkingLot {

    private String id;
    private String name;
    private Address address;
    private Money baseRate = Money.of(5.00);
    private LotType type;
    private ParkingChargeStrategy strategy;
    private ArrayList<ParkingObserver> observers = new ArrayList<>();
    private ParkingEvent event;

    public ParkingLot(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ParkingLot(String id, String name, Address address, Money baseRate, LotType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.baseRate = baseRate;
        this.type = type;
    }

    public Money getParkingCharges(ParkingPermit p, LocalDateTime in, LocalDateTime out) {
        return strategy.parkingCharge(this, in, out, p);
    }

    public Money getBaseRate() {
        return baseRate;
    }

    public LotType getType() {
        return this.type;
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

    public ParkingEvent getParkingEvent() {
        return event;
    }

    public void setParkingEvent(ParkingEvent event) {
        this.event = event;
    }

    public ParkingChargeStrategy getParkingChargeStrategy() {
        return strategy;
    }

    public void setParkingChargeStrategy(String strategy) {
        if (strategy.equals("FlatRate")) {
            this.strategy = new BaseStrategyCharge();
        } else if (strategy.equals("Compact")) {
            this.strategy = new CompactDiscountCharge();
        } else if (strategy.equals("SpecialDay")) {
            this.strategy = new SpecialDayDiscountCharge();
        } else if (strategy.equals("Weekend")) {
            this.strategy = new WeekendDiscountCharge();
        } else {
            throw new IllegalArgumentException(strategy + "is not a valid charging strategy");
        }
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

    public ArrayList<ParkingObserver> addObserver(ParkingObserver observer) {
        observers.add(observer);
        return observers;
    }

    public ArrayList<ParkingObserver> removeObserver(ParkingObserver observer) {
        observers.remove(observer);
        return observers;
    }

    public void notifyObservers() {
        for (ParkingObserver observer : observers) {
            observer.update(event);
        }
    }
}
