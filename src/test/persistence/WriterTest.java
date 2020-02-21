package persistence;

import model.emission.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_LOG_FILE = "./data/testCarbonLogFile.txt";
    private static final String TEST_EM_FILE = "./data/testEmissionsFile.txt";
    private Writer emissionWriter;
    private Writer footprintLogWriter;
    private CarbonFootprintLog carbonLog;
    private Diet diet;
    private HomeEnergy elec;
    private HomeEnergy gas;
    private HomeEnergy oil;
    private Transportation bus;
    private Vehicle car;

    @BeforeEach
    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        emissionWriter = new Writer(new File(TEST_EM_FILE));
        footprintLogWriter = new Writer(new File(TEST_LOG_FILE));

        carbonLog = new CarbonFootprintLog("UZBEKISTAN");

        diet = new Diet(DietType.MEDIUM_MEAT);
        elec = new HomeEnergy(EnergyType.ELECTRICITY);
        gas = new HomeEnergy(EnergyType.GAS);
        oil = new HomeEnergy(EnergyType.OIL);
        bus = new Transportation();
        car = new Vehicle();

        diet.setCalPerDay(2000);
        elec.setMonthlyKwh(1000);
        gas.setMonthlyKwh(1000);
        oil.setMonthlyKwh(1000);
        bus.setDistancePerDay(30);
        car.setDistancePerDay(2);
    }

    @Test
    public void testWriteEmissions() {
        // save emission sources to file
        emissionWriter.write(diet);
        emissionWriter.write(elec);
        emissionWriter.write(gas);
        emissionWriter.write(oil);
        emissionWriter.write(bus);
        emissionWriter.write(car);

        verifyEmissionSources();
    }

    @Test
    public void testWriteCarbonLog() {
        carbonLog.addCarbonSource(diet);
        carbonLog.addCarbonSource(elec);
        carbonLog.addCarbonSource(gas);
        carbonLog.addCarbonSource(oil);
        carbonLog.addCarbonSource(bus);
        carbonLog.addCarbonSource(car);

        footprintLogWriter.write(carbonLog);

        try {
            carbonLog = Reader.readCarbonFootprintLog(new File(TEST_LOG_FILE));
            List<CarbonEmission> emissionSources = carbonLog.getEmissionSources();
            diet = (Diet) emissionSources.get(0);
            elec = (HomeEnergy) emissionSources.get(1);
            gas = (HomeEnergy) emissionSources.get(2);
            oil = (HomeEnergy) emissionSources.get(3);
            bus = (Transportation) emissionSources.get(4);
            car = (Vehicle) emissionSources.get(5);

            verifyEmissionSources();

            assertEquals("UZBEKISTAN", carbonLog.getCountry());

            // check that nextLog id is set correctly
            CarbonFootprintLog nextLog = new CarbonFootprintLog("ALGERIA");
            assertEquals(2, nextLog.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyEmissionSources() {
        // read back and verify sources have expected values
        try {
            List<CarbonEmission> carbonEmissions = Reader.readCarbonEmissions(new File("./data/testCarbonEmissionsFile.txt"));

            Diet diet = (Diet) carbonEmissions.get(0);
            assertEquals(2000 * 365 * (Diet.MEDMEATEATER_EF / 2000), diet.getCarbonEmission());
            assertEquals(DietType.MEDIUM_MEAT, diet.getDietType());
            assertEquals(2000, diet.getCalPerDay());

            HomeEnergy electricity = (HomeEnergy) carbonEmissions.get(1);
            assertEquals(1000 * 12 * HomeEnergy.ELECTRIC_EF, electricity.getCarbonEmission());
            assertEquals(EnergyType.ELECTRICITY, electricity.getCarbonEmission());
            assertEquals(1000, electricity.getMonthlyKwh());

            HomeEnergy gas = (HomeEnergy) carbonEmissions.get(2);
            assertEquals(1000 * 12 * HomeEnergy.GAS_EF, gas.getCarbonEmission());
            assertEquals(EnergyType.GAS, gas.getCarbonEmission());
            assertEquals(1000, gas.getMonthlyKwh());

            HomeEnergy oil = (HomeEnergy) carbonEmissions.get(3);
            assertEquals(1000 * 12 * HomeEnergy.OIL_EF, oil.getCarbonEmission());
            assertEquals(EnergyType.OIL, oil.getCarbonEmission());
            assertEquals(1000, oil.getMonthlyKwh());

            Transportation trans = (Transportation) carbonEmissions.get(4);
            assertEquals(30 * 365 * Transportation.BUS_EF, trans.getCarbonEmission());
            assertEquals(30, trans.getDistance());

            Vehicle car = (Vehicle) carbonEmissions.get(5);
            assertEquals((2 * 365 *  Vehicle.MILES_PER_KM / Vehicle.AVG_MPG), car.getCarbonEmission());
            assertEquals(2, car.getDistance());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }

}
