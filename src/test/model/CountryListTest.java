package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryListTest {

    HashMap<String, Double> countries;

    @BeforeEach
    public void setUp() {
        countries = CountryList.getCountries();
    }

    @Test
    public void testGetCountryAvg() {
        assertEquals(15.12, countries.get("CANADA"));
        assertEquals(0.16, countries.get("BURKINA FASO"));
    }
}
