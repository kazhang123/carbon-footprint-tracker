package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {
    Date testDate1;
    Date testDate2;
    Calendar c = Calendar.getInstance();

    @BeforeEach
    public void setUp() {
        testDate1 = new Date();
        testDate2 = new Date(1, 1, 2020);
    }

    @Test
    public void testConstructor() {
        assertEquals(c.get(Calendar.MONTH) + 1, testDate1.getMonth());
        assertEquals(c.get(Calendar.DAY_OF_MONTH), testDate1.getDay());
        assertEquals(c.get(Calendar.YEAR), testDate1.getYear());
    }

    @Test
    public void testUpdateDate() {
        assertEquals(1, testDate2.getMonth());
        assertEquals(1, testDate2.getDay());
        assertEquals(2020, testDate2.getYear());
        testDate2.updateDate();
        assertEquals(c.get(Calendar.MONTH) + 1, testDate2.getMonth());
        assertEquals(c.get(Calendar.DAY_OF_MONTH), testDate2.getDay());
        assertEquals(c.get(Calendar.YEAR), testDate2.getYear());
    }

}
