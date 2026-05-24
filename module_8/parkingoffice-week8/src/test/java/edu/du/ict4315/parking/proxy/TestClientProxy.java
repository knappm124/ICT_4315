package edu.du.ict4315.parking.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.ParkingTransaction;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.proxy.ClientProxy;

class TestClientProxy {

  private static RealParkingOffice realOffice ;
  private static Address parkingOfficeAddress;
  private static String parkingOfficeName = "Test Office";
  private ClientProxy office;
  
  @BeforeAll
  static void setUp() {
    realOffice = new RealParkingOffice();
    realOffice.setParkingOfficeName(parkingOfficeName);
    parkingOfficeAddress = new Address.Builder().withStreetAddress1("129 Main St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    realOffice.setParkingOfficeAddress(parkingOfficeAddress);
  }
  
  @BeforeEach
  void setup() {
    office = new ClientProxy(realOffice);
  }

  @Test
  final void testClientProxy() {
    assertTrue(office.getParkingOfficeName().equals(parkingOfficeName));
  }

  @Test
  final void testPark() {
    Address address = new Address.Builder().withStreetAddress1("129 Main St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer("C9999", "Jim", "Johnson", "303-555-1212", address);
    office.register(customer);
    Car car = new Car(CarType.COMPACT, "CO-1234", customer);
    String permitId = office.register(car);
    ParkingPermit permit = office.getParkingPermit(permitId);
    ParkingLot lot = office.getParkingLot("W");
    LocalDateTime in = LocalDateTime.of(2020, 7, 21, 10, 0, 1);
    ParkingTransaction t = office.park(in, permit, lot);
    ParkingTransaction t2 = realOffice.park(in, permit, lot);
    assertTrue(t.getParkingLot()==t2.getParkingLot());
    assertTrue(t.getChargedAmount() == t2.getChargedAmount());
    assertTrue(t.getDateIn().equals(t2.getDateIn()));
  }

  @Test
  final void testGetCustomer() {
    String customerId = "Z";
    Address address = new Address.Builder().withStreetAddress1("119 Main St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer(customerId, "Pat", "Doe", "303-555-1212", address);
    String id = office.register(customer);
    assertTrue(id.equals(customerId));
    assertTrue(customer == office.getCustomer(id));
    // Test C9999?
  }

  @Test
  final void testGetParkingPermit() {
    Address address = new Address.Builder().withStreetAddress1("109 Main St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer("Y", "Jim", "Johnson", "303-555-1212", address);
    office.register(customer);
    Car car = new Car(CarType.COMPACT, "CO-4567", customer);
    String permitId = office.register(car);
    ParkingPermit permit = office.getParkingPermit(permitId);
    assertTrue(permit.getId().equals(permitId));
    assertTrue(permit.getCar() == car);
  }

  @Test
  final void testGetParkingLot() {
    // Lots are W, 108, 321, 301
    assertTrue(office.getParkingLot("W") != null);
    assertTrue(office.getParkingLot("Z") == null );
    assertTrue(office.getParkingLot("108") != null );
    assertTrue(office.getParkingLot("301") != null );
    assertTrue(office.getParkingLot("321") != null );
    assertTrue(office.getParkingLot("300") == null );    
  }

  @Test
  final void testGetParkingOfficeAddress() {
    assertTrue(office.getParkingOfficeAddress() == parkingOfficeAddress);
  }

  @Test
  final void testRegisterCar() {
    Address address = new Address.Builder().withStreetAddress1("129 Second St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer("X", "Jim", "Johnson", "313-555-1212", address);
    office.register(customer);
    Car car = new Car(CarType.COMPACT, "CO-8754", customer);
    String permitId = office.register(car);
    assertFalse(permitId.isEmpty());
  }

  @Test
  final void testRegisterCustomer() {
    Address address = new Address.Builder().withStreetAddress1("109 Second St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer("W", "Jim", "Johnson", "303-555-1212", address);
    String id = office.register(customer);
    assertEquals(id, customer.getId());
  }

  @Test
  final void testGetParkingOfficeName() {
    assertEquals(office.getParkingOfficeName(), parkingOfficeName);
  }

}
