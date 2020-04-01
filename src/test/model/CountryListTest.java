package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryListTest {

    HashMap<String, Double> countries;
    CountryList countryList;

    @BeforeEach
    public void setUp() {
        countries = CountryList.getInstance().getCountries();
        countryList = new CountryList();
    }

    @Test
    public void testCountryListSize() {
        assertEquals(208, countryList.getSize());
        assertEquals(208, countryList.getInstance().getCountries().size());
    }

    @Test
    public void testGetCountryAvg() {
        assertEquals(15.12, CountryList.getInstance().getCountries().get("CANADA"));
        assertEquals(0.16, CountryList.getInstance().getCountries().get("BURKINA FASO"));
        assertEquals(16.49, CountryList.getInstance().getCountries().get("USA"));
    }


}
