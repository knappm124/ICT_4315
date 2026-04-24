 //////////////////////////
// This class represents a parking transaction event.
// This class is immutable.
// File: ParkingTransaction.java
// Author: M. I. Schwartz
//////////////////////////
package edu.du.ict4315.parking;

import java.time.Instant;
import java.time.LocalDateTime;

import edu.du.ict4315.currency.Money;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;

public class ParkingTransaction {

    private Instant transactionDate;
    private LocalDateTime date;
    private ParkingPermit permit;
    private ParkingLot parkingLot;
    private Money chargedAmount;

    public ParkingTransaction(LocalDateTime d, ParkingPermit p, ParkingLot l, Money m) {
        transactionDate = Instant.now();
        date = d;
        permit = p;
        parkingLot = l;
        //Convert end date from instant to localdatetime and calculate hourly difference
        LocalDateTime ldt = LocalDateTime.ofInstant(transactionDate, ZoneOffset.of("-07:00"));
        Duration between = Duration.between(date,ldt);
        int hours = (int) between.toHours();
        //Create day HashMap
        String nextDay = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        HashMap<String,Boolean> days = new HashMap();
        
        //Loop through days until ldt is reached, and populate hash map with values
        for(int i = 0; i < (int)between.toDays(); i++){
            days.put(nextDay, false);
            d.plusDays(1);
            nextDay = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        }
        chargedAmount = l.getParkingChargeStrategy().parkingCharge(l, days, hours, permit);
    }

    public Money getChargedAmount() {
        return chargedAmount;
    }

    public ParkingPermit getPermit() {
        return permit;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    // TODO: toString()
}
