///////////////////////
// File: TestFileLoader.java
// Author: M I Schwartz
// JUnit 5 tests for the FileLoader class
///////////////////////
package edu.du.ict4315.parking.support.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.du.ict4315.currency.Money;
import edu.du.ict4315.parking.Address;
import edu.du.ict4315.parking.ParkingLot;
import edu.du.ict4315.parking.RealParkingOffice;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFileLoader {

    // Test data based on the data in the load files
    private final String[] lotIds = {"W", "108", "301", "321"};
    private final String[][] lotNames = {
        {"W", "Lot W"},
        {"108", "Lot 108"},
        {"301", "Lot 301"},
        {"321", "Lot 321"}
    };
    private final Money[] lotBaseRates = {
        Money.of(5.00),
        Money.of(2.00),
        Money.of(8.00),
        Money.of(8.00)
    };

    private final String[][] addresses = {
        {"W", "E Jewell Ave", "", "Denver", "CO", "80210"},
        {"108", "E Buchtel Ave", "", "Denver", "CO", "80210"},
        {"321", "S Gaylord St", "", "Denver", "CO", "80210"},
        {"301", "E Evans Ave", "", "Denver", "CO", "80210"},};

    private void checkName(RealParkingOffice office) {
        for (String[] lotName : lotNames) {
            ParkingLot lot = office.getParkingLot(lotName[0]);
            assertTrue(lot.getName().contentEquals(lotName[1]));
        }
    }

    private void checkAddress(RealParkingOffice office) {
        for (String[] address : addresses) {
            ParkingLot lot = office.getParkingLot(address[0]);
            Address a = lot.getAddress();
            assertTrue(a.getCity().equals(address[3]));
            assertTrue(a.getState().equals(address[4]));
            assertTrue(a.getZip().equals(address[5]));
            assertTrue(a.getStreetAddress1().equals(address[1]));
            assertTrue(a.getStreetAddress2().equals(address[2]));
        }
    }

    private void checkBaseRate(RealParkingOffice office) {
        for (int i = 0; i < lotIds.length; i++) {
            ParkingLot lot = office.getParkingLot(lotIds[i]);
            // Set the rate in case it has been reset elsewhere
            Money save = lot.getBaseRate();
            lot.setBaseRate(lotBaseRates[i]);

            System.out.println("Lot " + lotNames[i][1] + "/" + lot.getName() + "; Rate: " + lotBaseRates[i] + "/" + lot.getBaseRate());
            assertEquals(lotBaseRates[i], lot.getBaseRate());

            // Reset the rate to what it was.
            lot.setBaseRate(save);
        }
    }

    private void checkParkingLotInitialization(RealParkingOffice office) {
        // Check names
        checkName(office);

        // Check addresses
        checkAddress(office);

        // Check base rates
        checkBaseRate(office);

    }

    @Test
    final void testInitialization() {
        RealParkingOffice office = new RealParkingOffice();
        checkParkingLotInitialization(office);
    }

}
