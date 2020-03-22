package model;

import model.emission.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarbonEmissionTest {

    Diet dietLM;
    Diet dietMM;
    Diet dietHM;
    Diet dietP;
    Diet dietVege;
    Diet dietVegan;
    HomeEnergy homeEnergyE;
    HomeEnergy homeEnergyO;
    HomeEnergy homeEnergyG;
    Transportation transportation;
    Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        dietLM = new Diet(DietType.LOW_MEAT);
        dietMM = new Diet(DietType.MEDIUM_MEAT);
        dietHM = new Diet(DietType.HIGH_MEAT);
        dietP = new Diet(DietType.PESCETARIAN);
        dietVege = new Diet(DietType.VEGETARIAN);
        dietVegan = new Diet(DietType.VEGAN);

        homeEnergyE = new HomeEnergy(EnergyType.ELECTRICITY);
        homeEnergyG = new HomeEnergy(EnergyType.GAS);
        homeEnergyO = new HomeEnergy(EnergyType.OIL);

        transportation = new Transportation();
        vehicle = new Vehicle();
    }

    @Test
    public void testDietConstructor() {
        assertEquals(0, dietLM.getCarbonEmission());
        assertEquals(dietLM.getDietType(), DietType.LOW_MEAT);
        assertEquals(dietMM.getDietType(), DietType.MEDIUM_MEAT);
        assertEquals(dietHM.getDietType(), DietType.HIGH_MEAT);
        assertEquals(dietP.getDietType(), DietType.PESCETARIAN);
        assertEquals(dietVege.getDietType(), DietType.VEGETARIAN);
        assertEquals(dietVegan.getDietType(), DietType.VEGAN);
        assertEquals(10000, dietLM.getMax());
        assertEquals(0, dietLM.getValue());
    }

    // test whether calculation method is multiplying emissions factor correctly
    @Test
    public void testDietCalculation() {
        dietLM.calculateCarbonEmission(2000);
        assertEquals(2000 * 365 * (Diet.LOWMEATEATER_EF / 2000) , dietLM.getCarbonEmission());
        assertEquals(2000, dietLM.getCalPerDay());
        dietLM.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.LOWMEATEATER_EF / 2000), dietLM.getCarbonEmission());
        dietLM.setDietType(DietType.VEGETARIAN);
        assertEquals( 4000 * 365 * (Diet.VEGETARIAN_EF / 2000), dietLM.getCarbonEmission());


        dietMM.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.MEDMEATEATER_EF / 2000), dietMM.getCarbonEmission());

        dietHM.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.HIGHMEATEATER_EF / 2000), dietHM.getCarbonEmission());

        dietP.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.PESCETARIAN_EF / 2000), dietP.getCarbonEmission());

        dietVege.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.VEGETARIAN_EF / 2000), dietVege.getCarbonEmission());

        dietVegan.calculateCarbonEmission(4000);
        assertEquals(4000  * 365 * (Diet.VEGAN_EF / 2000), dietVegan.getCarbonEmission());
    }

    @Test
    public void testTransportationConstructor() {
        assertEquals(0, transportation.getCarbonEmission());
        assertEquals(0, transportation.getDistance());
        assertEquals(200, transportation.getMax());
        assertEquals(0, transportation.getValue());
    }

    @Test
    public void testTransportationCalculation() {
        transportation.calculateCarbonEmission(1000);
        assertEquals(1000 * 365  * Transportation.BUS_EF, transportation.getCarbonEmission());
        assertEquals(1000, transportation.getDistance());
        transportation.calculateCarbonEmission(500);
        assertEquals(500  * 365 * Transportation.BUS_EF, transportation.getCarbonEmission());
    }

    @Test
    public void testVehicleConstructor() {
        assertEquals(0, vehicle.getDistance());
        assertEquals(0, vehicle.getCarbonEmission());
        assertEquals(200, vehicle.getMax());
        assertEquals(0, vehicle.getValue());
    }

    // test whether calculation method is multiplying emissions factor correctly
    @Test
    public void testVehicleCalculations() {
        vehicle.calculateCarbonEmission(45);
        assertEquals((45 * 365 * 0.62 / Vehicle.AVG_MPG) * Vehicle.GASOLINE_EF, vehicle.getCarbonEmission());
        assertEquals(45, vehicle.getDistance());
    }

    @Test
    public void testHomeEnergyConstructor() {
        assertEquals(0, homeEnergyE.getCarbonEmission());
        assertEquals(0, homeEnergyE.getMonthlyKwh());
        assertEquals(homeEnergyE.getEnergyType(),EnergyType.ELECTRICITY);
        assertEquals(5000, homeEnergyE.getMax());
        assertEquals(0, homeEnergyE.getValue());

        assertEquals(0, homeEnergyG.getCarbonEmission());
        assertEquals(0, homeEnergyG.getMonthlyKwh());
        assertEquals(homeEnergyG.getEnergyType(), EnergyType.GAS);

        assertEquals(0, homeEnergyO.getCarbonEmission());
        assertEquals(0, homeEnergyO.getMonthlyKwh());
        assertEquals(homeEnergyO.getEnergyType(), EnergyType.OIL);
    }

    // test whether calculation method is multiplying emission factors correctly
    @Test
    public void testHomeEnergyCalculation() {
        homeEnergyO.calculateCarbonEmission(1000);
        assertEquals(1000 * 12 * HomeEnergy.OIL_EF, homeEnergyO.getCarbonEmission());
        assertEquals(1000, homeEnergyO.getMonthlyKwh());

        homeEnergyG.calculateCarbonEmission(1000);
        assertEquals(1000 * 12 * HomeEnergy.GAS_EF, homeEnergyG.getCarbonEmission());

        homeEnergyE.calculateCarbonEmission(1000);
        assertEquals(1000 * 12 * HomeEnergy.ELECTRIC_EF, homeEnergyE.getCarbonEmission());
    }

    @Test
    public void testToString() {
        assertEquals("Diet: " + String.format("%.2f", dietLM.getCarbonEmission()), dietLM.toString());
        assertEquals("Electricity: "
                + String.format("%.2f", homeEnergyE.getCarbonEmission()), homeEnergyE.toString());
        assertEquals("Oil: " + String.format("%.2f", homeEnergyO.getCarbonEmission()), homeEnergyO.toString());

        homeEnergyG.calculateCarbonEmission(1200);
        assertEquals("Gas: " + String.format("%.2f", homeEnergyG.getCarbonEmission()), homeEnergyG.toString());
        assertEquals("Transportation: " +
                String.format("%.2f", transportation.getCarbonEmission()), transportation.toString());
        assertEquals("Vehicle: " + String.format("%.2f", vehicle.getCarbonEmission()), vehicle.toString());

    }




}