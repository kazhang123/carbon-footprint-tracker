package model;

import model.emissions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarbonFootprintLogTest {

    CarbonFootprintLog testCarbonLog;
    Diet diet;
    HomeEnergy energy;
    Transportation bus;
    Vehicle car;

    @BeforeEach
    public void setUp() {
        testCarbonLog = new CarbonFootprintLog("Canada");
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
        assertEquals("Canada", testCarbonLog.getCountry());
        assertEquals(CarbonFootprintLog.CAN_AVG, testCarbonLog.getAvgCountryFootprint());
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

//    @Test
//    public void testAddExistingSource() {
//        testCarbonLog.addCarbonSource(diet);
//        testCarbonLog.addCarbonSource(energy);
//        HomeEnergy energy2 = new HomeEnergy(EnergyType.ELECTRICITY);
//        energy2.setMonthlyKwh(30);
//
//        assertEquals(diet.getCarbonFootprint() + energy.getCarbonFootprint(),
//                testCarbonLog.getTotalEmission());
//        testCarbonLog.addCarbonSource(energy2);
//        assertEquals(diet.getCarbonFootprint() + energy2.getCarbonFootprint(),
//                testCarbonLog.getTotalEmission());
//
//        HomeEnergy energy3 = new HomeEnergy(EnergyType.GAS);
//        energy3.setMonthlyKwh(50);
//
//        testCarbonLog.addCarbonSource(energy3);
//        assertEquals(diet.getCarbonFootprint() + energy2.getCarbonFootprint() + energy3.getCarbonFootprint(),
//                testCarbonLog.getTotalEmission());
//
//    }

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
        assertEquals(CarbonFootprintLog.USA_AVG, testCarbonLog.getAvgCountryFootprint());
        testCarbonLog.setCountry("Uzbekistan");
        assertEquals(CarbonFootprintLog.WORLD_AVG, testCarbonLog.getAvgCountryFootprint());
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
