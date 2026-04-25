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

    private ParkingTransaction(ParkingTransactionBuilder parkingTransactionBuilder) {
        this.transactionDate = parkingTransactionBuilder.transactionDate;
        this.date = parkingTransactionBuilder.date;
        this.permit = parkingTransactionBuilder.permit;
        this.parkingLot = parkingTransactionBuilder.parkingLot;
        this.chargedAmount = parkingTransactionBuilder.chargedAmount;
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
    
    public static class ParkingTransactionBuilder {
        private Instant transactionDate;
        private LocalDateTime date;
        private ParkingPermit permit;
        private ParkingLot parkingLot;
        private Money chargedAmount;
        
        public ParkingTransactionBuilder(ParkingLot parkingLot) {
            this.transactionDate = Instant.now();
            this.parkingLot = parkingLot;
        }
        
        public ParkingTransactionBuilder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }
        
        public ParkingTransactionBuilder withParkingPermit(ParkingPermit permit){
            this.permit = permit;
            return this;
        }
        
        public ParkingTransactionBuilder withMoney(Money chargedAmount){
            this.chargedAmount = chargedAmount;
            return this;
        }
        
        public ParkingTransaction build() {
            return new ParkingTransaction(this);
        }
    }
}
