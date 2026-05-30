package edu.du.ict4315.parking.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.charges.factory.FactoryFlatRate;
import edu.du.ict4315.parking.proxy.AdminProxy;
import edu.du.ict4315.parking.support.User;

class TestAdminProxy {

  private static RealParkingOffice realOffice;
  private static Address parkingOfficeAddress;
  private static String parkingOfficeName = "Admin Test Office";
  private AdminProxy office;

  @BeforeAll
  static void setup() {
    realOffice = new RealParkingOffice();
    realOffice.setParkingOfficeName(parkingOfficeName);
    parkingOfficeAddress = new Address.Builder().withStreetAddress1("129 Main St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    realOffice.setParkingOfficeAddress(parkingOfficeAddress);
  }

  @BeforeEach
  void setUp() throws Exception {
    // TODO: Get passwd from user input or environment.
    User user = realOffice.authorizeUser("admin", "admin");
    office = new AdminProxy(realOffice, user);
  }

  // Omitting all tests already covered in the TestClientProxy class,
  // as this interface merely extends it.
  // Tests should be reinserted if they depend on the user constructing the proxy
  @Test
  final void testAdminProxy() {
    assertEquals(office.getParkingOfficeName(), parkingOfficeName);
  }

  @Test
  final void testSetParkingOfficeAddress() {
    Address newParkingOfficeAddress
        = new Address.Builder().withStreetAddress1("129 Second St.").withCity("Denver").withState(
            "CO").withZip("80208").build();
    // First, make sure it is set to the old address
    assertEquals(office.getParkingOfficeAddress(), parkingOfficeAddress);
    // Change the address
    office.setParkingOfficeAddress(newParkingOfficeAddress);
    // It should not be equal to the old address
    assertNotEquals(office.getParkingOfficeAddress(), parkingOfficeAddress);
    // It should be equals to the new address
    assertEquals(office.getParkingOfficeAddress(), newParkingOfficeAddress);
    // For consistency, we'll set it back to its original value
    office.setParkingOfficeAddress(parkingOfficeAddress);
  }

  @Test
  final void testSetParkingOfficeName() {
    String newParkingOfficeName = "Admin Proxy Test Name";
    assertEquals(office.getParkingOfficeName(), parkingOfficeName);
    office.setParkingOfficeName(newParkingOfficeName);
    assertNotEquals(office.getParkingOfficeName(), parkingOfficeName);
    assertEquals(office.getParkingOfficeName(), newParkingOfficeName);
    // For consistency, we'll set it back to its original value
    office.setParkingOfficeName(parkingOfficeName);
  }

  @Test
  final void testGetParkingChargesCustomerAndPermit() {
    // Lets rack up some charges
    // Since we are not testing the computation of charges themselves, we'll set a lot to flat rate for this purpose.
    // Create a customer
    Address address = new Address.Builder().withStreetAddress1("129 Third St.").withCity(
        "Denver").withState("CO").withZip("80208").build();
    Customer customer = new Customer("Q", "Robin", "Hood", "303-555-1212", address);
    String custId = office.register(customer);
    // Create two cars, both registered to the same customer
    Car car1 = new Car(CarType.COMPACT, "DU-1234", customer);
    Car car2 = new Car(CarType.SUV, "DU-4567", customer);
    String p1 = office.register(car1);
    String p2 = office.register(car2);
    ParkingLot lot = office.getParkingLot("W");
    lot.setParkingChargeCalculatorFactory(new FactoryFlatRate());
    Money baseRate = lot.getBaseRate();

    // Create 2 parking charges for each car
    lot.enterLot(LocalDateTime.of(2020, 5, 1, 10, 0, 5), p1);
    lot.enterLot(LocalDateTime.of(2020, 5, 2, 10, 0, 5), p1);
    lot.enterLot(LocalDateTime.of(2020, 5, 3, 10, 0, 5), p2);
    lot.enterLot(LocalDateTime.of(2020, 5, 4, 10, 0, 5), p2);

    // Each car should be charged 2*baseRate;
    // The customer should be charged 4*baseRate
    Money left;
    Money right;
    left = office.getParkingCharges(office.getParkingPermit(p1));
    right = Money.times(baseRate, 2);
    assertEquals(left, right);

    left = office.getParkingCharges(office.getParkingPermit(p2));
    right = Money.times(baseRate, 2);
    assertEquals(left, right);

    left = office.getParkingCharges(office.getCustomer(custId));
    right = Money.times(baseRate, 4);
    assertEquals(left, right);
  }

}
