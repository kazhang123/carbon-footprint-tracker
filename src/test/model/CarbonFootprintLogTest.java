package model;

import model.emission.*;
import model.emission.exception.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarbonFootprintLogTest {

    Calendar c = Calendar.getInstance();
    CarbonFootprintLog testCarbonLog;
    Diet diet;
    HomeEnergy energy;
    Transportation bus;
    Vehicle car;

    @BeforeEach
    public void setUp() throws NegativeAmountException {
        testCarbonLog = new CarbonFootprintLog("CANADA");
        diet = new Diet(DietType.MEDIUM_MEAT);
        energy = new HomeEnergy(EnergyType.ELECTRICITY);
        bus = new Transportation();
        car = new Vehicle();

        diet.calculateCarbonEmission(2000);
        energy.calculateCarbonEmission(50);
        bus.calculateCarbonEmission(30);
        car.calculateCarbonEmission(2);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testCarbonLog.getTotalEmission());
        assertEquals("CANADA", testCarbonLog.getCountry());
        assertEquals(15.12, testCarbonLog.getAvgCountryFootprint());
        assertEquals(0, testCarbonLog.getEmissionSources().size());
        assertEquals(c.get(Calendar.MONTH) + 1, testCarbonLog.getMonth());
        assertEquals(c.get(Calendar.DAY_OF_MONTH), testCarbonLog.getDay());
        assertEquals(c.get(Calendar.YEAR), testCarbonLog.getYear());
    }

    @Test
    public void testAddOneSource() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(diet.getCarbonEmission(), testCarbonLog.getTotalEmission());
        assertEquals(1, testCarbonLog.getEmissionSources().size());
    }

    @Test
    public void testAddManySources() {
        testCarbonLog.addCarbonSource(diet);
        testCarbonLog.addCarbonSource(energy);
        testCarbonLog.addCarbonSource(bus);
        testCarbonLog.addCarbonSource(car);
        assertEquals(diet.getCarbonEmission() + energy.getCarbonEmission() +
                        bus.getCarbonEmission() + car.getCarbonEmission(),
                testCarbonLog.getTotalEmission());
        assertEquals(4, testCarbonLog.getEmissionSources().size());
    }

    @Test
    public void testNumTreesToOffset() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(Math.round(diet.getCarbonEmission() / CarbonFootprintLog.CARBON_PER_TREE),
                testCarbonLog.numTreesToOffset(testCarbonLog.getTotalEmission()));
        testCarbonLog.addCarbonSource(energy);
        testCarbonLog.addCarbonSource(bus);
        testCarbonLog.addCarbonSource(car);
        assertEquals(Math.round(testCarbonLog.getTotalEmission() / CarbonFootprintLog.CARBON_PER_TREE),
                testCarbonLog.numTreesToOffset(testCarbonLog.getTotalEmission()));
    }

    @Test
    public void testAvgCarbonFootprint() {
        testCarbonLog.setCountry("USA");
        assertEquals("USA", testCarbonLog.getCountry());
        assertEquals(16.49, testCarbonLog.getAvgCountryFootprint());
        testCarbonLog.setCountry("UZBEKISTAN");
        assertEquals(3.42, testCarbonLog.getAvgCountryFootprint());
        testCarbonLog.setCountry("ALGERIA");
        assertEquals(3.72, testCarbonLog.getAvgCountryFootprint());
    }

    @Test
    public void testPercentageEmission() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(100, testCarbonLog.percentageEmission(diet));
        testCarbonLog.addCarbonSource(energy);
        assertEquals(Math.round(diet.getCarbonEmission() / testCarbonLog.getTotalEmission() * 100),
                testCarbonLog.percentageEmission(diet));
    }

    @Test
    public void testUpdateCurrentLog() {
        testCarbonLog.updateDate();
        assertEquals(c.get(Calendar.MONTH) + 1, testCarbonLog.getMonth());
        assertEquals(c.get(Calendar.DAY_OF_MONTH), testCarbonLog.getDay());
        assertEquals(c.get(Calendar.YEAR), testCarbonLog.getYear());
    }


}
