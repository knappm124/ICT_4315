//////////////////////////
// This class keeps the collection of active transactions.
// In a larger system it might be implemented with a database
// File: TransactionManager.java
// Author: M. I. Schwartz
//////////////////////////
package edu.du.ict4315.parking;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.ParkingTransaction.ParkingTransactionBuilder;
import edu.du.ict4315.parking.charges.strategy.ParkingChargeStrategyFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// The methods of the transaction manager are implemented with the stream() interface (Java 8)
// This allows examples of map() and reduce(), as well as use of lambdas
// Of course, these aren't required for the solution. Do you think they are more elegant?
public class TransactionManager {
  
  private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());

  private List<ParkingTransaction> transactions = new ArrayList<ParkingTransaction>();
  private RealParkingOffice office;
  private static ParkingChargeStrategyFactory strategyFactory = new ParkingChargeStrategyFactory();

  public TransactionManager(RealParkingOffice office) {
    this.office = office;
  }

  public ParkingTransaction park(LocalDateTime d, ParkingPermit p, ParkingLot l) {
    ParkingTransaction transaction = null;
    if ( l != null && p != null ) {
        var chargeStrategy = strategyFactory.createStrategy("Base");
        
        //Convert end time to LocalDateTime and calculate hourly difference
        Instant n = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(n, ZoneOffset.of("-07:00"));
        Duration between = Duration.between(d,ldt);
        int hours = (int) between.toHours();
        
        //Create day HashMap
        String nextDay = d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        HashMap<String, Boolean> days = new HashMap();
        
        //Loop through days until ldt is reached, and populate HashMap with those values
        LocalDateTime tempDate = d;
        for(int i = 0; i < (int) between.toDays(); i++) {
            days.put(nextDay, false);
            tempDate = tempDate.plusDays(1);
            nextDay = tempDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        }
        
        Money money = chargeStrategy.parkingCharge(l, days, hours, p);
        transaction = new ParkingTransactionBuilder(l)
              .withDate(d)
              .withParkingPermit(p)
              .withMoney(money)
              .build();
      transactions.add(transaction);
    } else {
      
    }
    return transaction;
  }

  public Money getParkingCharges(Customer c) {
    List<ParkingTransaction> customerTransactions;
    // First, let's get a list of the transactions for the customer.
    customerTransactions = transactions.stream()
            .filter(transaction -> transaction.getPermit().getCar().getOwner().equals(c))
            .collect(Collectors.toList());

    // Now lets add up all the charged amounts
    Money result = customerTransactions.stream()
            .map(transaction -> transaction.getChargedAmount())
            .reduce(Money.of(0.0), (a, b) -> Money.add(a, b));

    return result;
  }

  public Money getParkingCharges(ParkingPermit p) {
    return transactions.stream()
            .filter(transaction -> transaction.getPermit().equals(p))
            .map(transaction -> transaction.getChargedAmount())
            .reduce(Money.of(0.0), (a, b) -> Money.add(a, b));
  }

}
