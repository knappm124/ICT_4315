/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.du.ict4315.parking.test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author melissa
 */
public class ParkingTransactionTest {

    public ParkingTransactionTest() {
    }

    /**
     * Test of getChargedAmount method, of class ParkingTransaction.
     */
    @Test
    public void testGetChargedAmount() {
        LocalDateTime d = LocalDateTime.of(2026, 04, 22, 05, 25);
        LocalDateTime e = LocalDateTime.of(2028, 01, 01, 00, 00);

        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica", "Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV, "35K2JE43", tempCustomer);
        ParkingPermit p = new ParkingPermit("1528", newCar, e);
        ParkingLot l = new ParkingLot("1", "Test Lot", lotAddress, Money.of(1.00));
        ParkingTransaction result = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        Money expResult = Money.of(78.00);
        assertEquals(expResult, result.getChargedAmount());
    }

    /**
     * Test of getPermit method, of class ParkingTransaction.
     */
    @Test
    public void testGetPermit() {
        LocalDateTime d = LocalDateTime.of(2026, 04, 22, 05, 25);
        LocalDateTime e = LocalDateTime.of(2028, 01, 01, 00, 00);

        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica", "Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV, "35K2JE43", tempCustomer);
        ParkingPermit p = new ParkingPermit("1528", newCar, e);
        ParkingLot l = new ParkingLot("1", "Test Lot", lotAddress, Money.of(1.00));
        ParkingTransaction result = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        assertEquals(p, result.getPermit());
    }

    /**
     * Test of getDate method, of class ParkingTransaction.
     */
    @Test
    public void testGetDate() {
        LocalDateTime d = LocalDateTime.of(2026, 04, 22, 05, 25);
        LocalDateTime e = LocalDateTime.of(2028, 01, 01, 00, 00);

        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica", "Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV, "35K2JE43", tempCustomer);
        ParkingPermit p = new ParkingPermit("1528", newCar, e);
        ParkingLot l = new ParkingLot("1", "Test Lot", lotAddress, Money.of(1.00));
        ParkingTransaction result = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        assertEquals(d, result.getDate());
    }

    /**
     * Test of getParkingLot method, of class ParkingTransaction.
     */
    @Test
    public void testGetParkingLot() {
        LocalDateTime d = LocalDateTime.of(2026, 04, 22, 05, 25);
        LocalDateTime e = LocalDateTime.of(2028, 01, 01, 00, 00);

        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica", "Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV, "35K2JE43", tempCustomer);
        ParkingPermit p = new ParkingPermit("1528", newCar, e);
        ParkingLot l = new ParkingLot("1", "Test Lot", lotAddress, Money.of(1.00));
        ParkingTransaction result = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        assertEquals(l, result.getParkingLot());
    }

    /**
     * Test of getTransactionDate method, of class ParkingTransaction.
     */
    @Test
    public void testGetTransactionDate() {
        LocalDateTime d = LocalDateTime.of(2026, 04, 22, 05, 25);
        LocalDateTime e = LocalDateTime.of(2028, 01, 01, 00, 00);

        //Set up customer, car, permit, and parking lot
        Address lotAddress = new Address.Builder()
                .withStreetAddress1("123 Main St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Address customerAddress = new Address.Builder()
                .withStreetAddress1("456 First St")
                .withCity("Denver")
                .withState("CO")
                .withZip("80208")
                .build();
        Customer tempCustomer = new Customer.CustomerBuilder("Jessica", "Jones")
                .withPhoneNumber("6278549652")
                .withAddress(customerAddress).build();
        Car newCar = new Car(CarType.SUV, "35K2JE43", tempCustomer);
        ParkingPermit p = new ParkingPermit("1528", newCar, e);
        ParkingLot l = new ParkingLot("1", "Test Lot", lotAddress, Money.of(1.00));
        ParkingTransaction result = new ParkingTransaction.ParkingTransactionBuilder(l)
                .withDate(d)
                .withMoney(Money.of(78.00))
                .withParkingPermit(p)
                .build();
        LocalDateTime n = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime temp = LocalDateTime.ofInstant(result.getTransactionDate(),ZoneId.systemDefault());
        assertEquals(temp.truncatedTo(ChronoUnit.SECONDS), n);
    }

}
