/*
 * Course: ICT
 * File: 
 * Author: Instructor
 */
package edu.du.ict4315.parking.charges.test;

import edu.du.ict4315.parking.charges.strategy.ParkingDays;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class ParkingDaysTest {

    private static String[] dateTimes = {
        "2022-01-22T10:00:00",
        "2022-01-22T12:00:00",
        "2022-01-22T14:00:00",
        "2022-01-22T16:00:00",
        "2022-01-22T19:00:00",
        "2022-01-22T23:00:00",};
    private static String[] durations = {
        "PT2H", "PT2H",
        "PT12H", "PT9H",
        "PT30H", "PT25H"
    };
    private static int[] expectedResults = {
        1, 1, 2, 2, 3, 3
    };
    private static int[] expectedResultsHours = {
        2, 2, 12, 9, 30, 25
    };

    private String printTest(LocalDateTime time, Duration dur, int result, int expected) {
        StringBuffer sb = new StringBuffer();
        sb.append("Start time: ");
        sb.append(time);
        sb.append("; Stop time: ");
        sb.append(time.plus(dur));
        sb.append("; result: ");
        sb.append(result);
        sb.append("; expected: ");
        sb.append(expected);
        return sb.toString();
    }

    @Test
    void testCount() {
        System.out.println("ParkingDays.count");
        for (int i = 0; i < dateTimes.length; i++) {
            LocalDateTime ldt = LocalDateTime.parse(dateTimes[i]);
            Duration duration = Duration.parse(durations[i]);
            int expectedResult = expectedResults[i];
            int result = ParkingDays.count(ldt, duration);
            System.out.println(printTest(ldt, duration, result, expectedResult));
            assertEquals(expectedResult, result);
        }
    }

    @Test
    void testHours() {
        System.out.println("ParkingDays.hours");
        for (int i = 0; i < dateTimes.length; i++) {
            LocalDateTime ldt = LocalDateTime.parse(dateTimes[i]);
            Duration duration = Duration.parse(durations[i]);
            int expectedResult = expectedResultsHours[i];
            int result = ParkingDays.hours(ldt, duration);
            System.out.println(printTest(ldt, duration, result, expectedResult));
            assertEquals(expectedResult, result);
        }

    }
}
