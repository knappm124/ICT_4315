package edu.du.ict4315.parking.observer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Car;
import edu.du.ict4315.parking.CarType;
import edu.du.ict4315.parking.Customer;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.ParkingPermit;
import edu.du.ict4315.parking.RealParkingOffice;
import edu.du.ict4315.parking.observer.ParkingAction;
import edu.du.ict4315.parking.observer.ParkingEvent;
import edu.du.ict4315.parking.observer.ParkingObserver;
import org.junit.jupiter.api.BeforeEach;

public class ParkingActionTest {

    static ParkingObserver observer;
    static ParkingEvent receivedEvent;
    static Money charges;
    static RealParkingOffice office;

    @BeforeAll
    public static void setUp() {
        office = new RealParkingOffice();
        observer = event -> {
            receivedEvent = (ParkingEvent) event.getNewValue();
            ParkingPermit permit = office.getParkingPermit(receivedEvent.getPermitId());
            charges = receivedEvent.getParkingLot()
                    .getParkingCharges(permit,
                            receivedEvent.getTimeIn(),
                            receivedEvent.getParkingLot()
                                    .getDuration(permit, receivedEvent.getTimeIn()));
            System.out.println("Event:   " + receivedEvent);
            System.out.println("Charges: " + charges);
        };
        System.out.println("Called setUp");
    }

    private ParkingLot lot;
    private Customer customer;
    private Car car;
    private ParkingPermit permit;

    @BeforeEach
    public void prePopulate() {

        System.out.println(office.getParkingLot("W"));

        lot = office.getParkingLot("W");
        customer = new Customer();
        customer.setId("C11");
        customer.setFirstName("Pat");
        customer.setLastName("Doe");
        customer.setPhoneNumber("303-555-1212");
        office.register(customer);
        car = new Car(CarType.COMPACT, "ABC123", customer);
        String permitId = office.register(car);
        permit = office.getParkingPermit(permitId);
        System.out.println("Called prePopulate");
    }
    private String[][] times = {
        {"2022-02-01T07:00:00", "2022-02-01T11:45:00"},};

    @Test
    public final void testObserverFramework() {
        ParkingAction action = new ParkingAction(lot);  // Add lot as observer
        action.addParkingObserver(observer);

        ParkingEvent event = new ParkingEvent(LocalDateTime.parse(times[0][0]),
                LocalDateTime.parse(times[0][1]),
                permit.getId(), lot, true);
        action.parkingNotification(event);
        System.out.println("Fired event");
        assertTrue(event.getTimeIn().compareTo(receivedEvent.getTimeIn()) == 0);
        assertTrue(event.getTimeOut().compareTo(receivedEvent.getTimeOut()) == 0);
        assertTrue(event.getParkingLot().getId().equals(receivedEvent.getParkingLot().getId()));
        assertTrue(event.getPermitId().equals(receivedEvent.getPermitId()));
    }

}
