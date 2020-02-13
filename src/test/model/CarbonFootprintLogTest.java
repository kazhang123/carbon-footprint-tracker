package model;

import model.emission.*;
import model.emission.exception.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarbonFootprintLogTest {

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

        diet.setCalPerDay(2000);
        energy.setMonthlyKwh(50);
        bus.setDistancePerDay(30);
        car.setDistancePerDay(2);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testCarbonLog.getTotalEmission());
        assertEquals("CANADA", testCarbonLog.getCountry());
        assertEquals(15.12, testCarbonLog.getAvgCountryFootprint());
    }

    @Test
    public void testAddOneSource() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(diet.getCarbonFootprint(), testCarbonLog.getTotalEmission());
    }

    @Test
    public void testAddManySources() {
        testCarbonLog.addCarbonSource(diet);
        testCarbonLog.addCarbonSource(energy);
        testCarbonLog.addCarbonSource(bus);
        testCarbonLog.addCarbonSource(car);
        assertEquals(diet.getCarbonFootprint() + energy.getCarbonFootprint() +
                        bus.getCarbonFootprint() + car.getCarbonFootprint(),
                testCarbonLog.getTotalEmission());
    }

    @Test
    public void testRemoveSource() {
        testCarbonLog.addCarbonSource(diet);
        testCarbonLog.addCarbonSource(energy);
        HomeEnergy oil = new HomeEnergy(EnergyType.OIL);
        testCarbonLog.addCarbonSource(oil);
        assertEquals(diet.getCarbonFootprint() + energy.getCarbonFootprint() + oil.getCarbonFootprint(),
                testCarbonLog.getTotalEmission());
        testCarbonLog.removeCarbonSource(oil);
        assertEquals(diet.getCarbonFootprint() + energy.getCarbonFootprint(),
                testCarbonLog.getTotalEmission());
        testCarbonLog.removeCarbonSource(energy);
        assertEquals(diet.getCarbonFootprint(), testCarbonLog.getTotalEmission());
    }

    @Test
    public void testNumTreesToOffset() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(Math.round(diet.getCarbonFootprint() / CarbonFootprintLog.CARBON_PER_TREE),
                testCarbonLog.numTreesToOffset());
        testCarbonLog.addCarbonSource(energy);
        testCarbonLog.addCarbonSource(bus);
        testCarbonLog.addCarbonSource(car);
        assertEquals(Math.round(testCarbonLog.getTotalEmission() / CarbonFootprintLog.CARBON_PER_TREE),
                testCarbonLog.numTreesToOffset());
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
        testCarbonLog.setCountry("AHHHHHHH");
    }

    @Test
    public void testPercentageEmission() {
        testCarbonLog.addCarbonSource(diet);
        assertEquals(100, testCarbonLog.percentageEmission(diet));
        testCarbonLog.addCarbonSource(energy);
        assertEquals(Math.round(diet.getCarbonFootprint() / testCarbonLog.getTotalEmission() * 100),
                testCarbonLog.percentageEmission(diet));
    }


}
