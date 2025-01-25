package wbb.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtilityTest {

    private final DateTimeUtility dtu = new DateTimeUtility();

    @Test
    public void testTryParseDateAndOrTime_dateTime() {
        String input = "25/12/2025 1230";
        String expected = "25th of December 2025, 12:30PM";
        assertEquals(expected, dtu.tryParseDateAndOrTime(input));
    }

    @Test
    public void testTryParseDateAndOrTime_dateOnly() {
        String input = "25/12/2025";
        String expected = "25th of December 2025";
        assertEquals(expected, dtu.tryParseDateAndOrTime(input));
    }

    @Test
    public void testTryParseDateAndOrTime_timeOnly() {
        String input = "2359";
        LocalDate today = LocalDate.now();
        String expected = dtu.formatDateTime(LocalDateTime.of(today, LocalTime.parse("23:59")));
        assertEquals(expected, dtu.tryParseDateAndOrTime(input));
    }

    @Test
    public void testTryParseDateAndOrTime_invalid() {
        String input = "invalid input";
        assertEquals(input, dtu.tryParseDateAndOrTime(input));
    }

    @Test
    public void testTryParseDateTime_valid() {
        String input = "25/12/2025 1230";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        String expected = dtu.formatDateTime(LocalDateTime.parse(input, formatter));
        assertEquals(expected, dtu.tryParseDateTime(input, formatter));
    }

    @Test
    public void testTryParseDateTime_pastDateTime() {
        String input = "25/12/2020 1230";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        assertNull(dtu.tryParseDateTime(input, formatter));
    }

    @Test
    public void testTryParseDate_valid() {
        String input = "25/12/2025";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String expected = dtu.formatDate(LocalDate.parse(input, formatter));
        assertEquals(expected, dtu.tryParseDate(input, formatter));
    }

    @Test
    public void testTryParseDate_pastDate() {
        String input = "25/12/2020";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        assertNull(dtu.tryParseDate(input, formatter));
    }

    @Test
    public void testTryParseTime_valid() {
        String input = "1500";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime expectedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0));
        if (expectedTime.isBefore(LocalDateTime.now())) {
            assertNull(dtu.tryParseTime(input, formatter));
        } else {
            String expected = dtu.formatDateTime(expectedTime);
            assertEquals(expected, dtu.tryParseTime(input, formatter));
        }
    }

    @Test
    public void testOrdinalDay() {
        assertEquals("1st", dtu.ordinalDay(1));
        assertEquals("2nd", dtu.ordinalDay(2));
        assertEquals("3rd", dtu.ordinalDay(3));
        assertEquals("4th", dtu.ordinalDay(4));
        assertEquals("11th", dtu.ordinalDay(11));
        assertEquals("21st", dtu.ordinalDay(21));
        assertEquals("22nd", dtu.ordinalDay(22));
        assertEquals("23rd", dtu.ordinalDay(23));
        assertEquals("31st", dtu.ordinalDay(31));
    }

    @Test
    public void testRemoveOrdinalDay() {
        assertEquals("1", dtu.removeOrdinalDay("1st"));
        assertEquals("2", dtu.removeOrdinalDay("2nd"));
        assertEquals("3", dtu.removeOrdinalDay("3rd"));
        assertEquals("4", dtu.removeOrdinalDay("4th"));
        assertEquals("11", dtu.removeOrdinalDay("11th"));
        assertEquals("21", dtu.removeOrdinalDay("21st"));
    }

    @Test
    public void testIsDueToday_true() {
        LocalDate today = LocalDate.now();
        String input = dtu.ordinalDay(today.getDayOfMonth()) + " of " + today.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        assertTrue(dtu.isDueToday(input));
    }

    @Test
    public void testIsDueToday_false() {
        String input = "1st of January 2025";
        assertFalse(dtu.isDueToday(input));
    }
}
