package persistence;

import model.emission.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSimpleWriterTest {
    private static final String TEST_FILE = "data/testJson3.json";
    private JsonSimpleWriter writer;
    private CarbonFootprintLog carbonLog;
    private Diet diet;
    private HomeEnergy elec;
    private HomeEnergy gas;
    private HomeEnergy oil;
    private Transportation bus;
    private Vehicle car;

    @BeforeEach
    public void setUp() throws IOException {
        writer = new JsonSimpleWriter(new File(TEST_FILE));

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
        bus.setDistancePerDay(40);
        car.setDistancePerDay(10);

        List<CarbonEmission> emissionList = Arrays.asList(diet, elec, gas, oil, bus, car);
        ArrayList<CarbonEmission> emissions = new ArrayList<>();
        emissions.addAll(emissionList);

        carbonLog = new CarbonFootprintLog("UZBEKISTAN", emissions);
    }

    @Test
    public void testWriteLogs() throws IOException {
        writer.write(carbonLog);
        writer.close();

        // verify that the carbon footprint log has expected values
        try {
            List<CarbonFootprintLog> carbonLogs = JsonSimpleReader.readJson(new File(TEST_FILE));
            CarbonFootprintLog carbonLog = carbonLogs.get(0);
            ArrayList<CarbonEmission> carbonEmissions = (ArrayList<CarbonEmission>) carbonLog.getEmissionSources();

            assertEquals("UZBEKISTAN", carbonLog.getCountry());

            Diet diet = (Diet) carbonEmissions.get(0);
            assertEquals(DietType.MEDIUM_MEAT, diet.getDietType());
            assertEquals(2000, diet.getCalPerDay());

            HomeEnergy electricity = (HomeEnergy) carbonEmissions.get(1);
            assertEquals(1000, electricity.getMonthlyKwh());

            HomeEnergy gas = (HomeEnergy) carbonEmissions.get(2);
            assertEquals(1000, gas.getMonthlyKwh());

            HomeEnergy oil = (HomeEnergy) carbonEmissions.get(3);
            assertEquals(1000, oil.getMonthlyKwh());

            Transportation trans = (Transportation) carbonEmissions.get(4);
            assertEquals(40, trans.getDistance());

            Vehicle car = (Vehicle) carbonEmissions.get(5);
            assertEquals(10, car.getDistance());
        } catch (ParseException e) {
            fail("exception should not have been thrown");
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }



//    @Test
//    public void testWriteEmissions() {
//        // save emission sources to file
//        writer.write(diet);
//        writer.write(elec);
//        writer.write(gas);
//        writer.write(oil);
//        writer.write(bus);
//        writer.write(car);
//
//        verifyEmissionSources();
//    }
//
//    @Test
//    public void testWriteCarbonLog() {
//        carbonLog.addCarbonSource(diet);
//        carbonLog.addCarbonSource(elec);
//        carbonLog.addCarbonSource(gas);
//        carbonLog.addCarbonSource(oil);
//        carbonLog.addCarbonSource(bus);
//        carbonLog.addCarbonSource(car);
//
//        footprintLogWriter.write(carbonLog);
//
//        try {
//            carbonLog = JsonSimpleReader.readCarbonFootprintLog(new File(TEST_LOG_FILE));
//            List<CarbonEmission> emissionSources = carbonLog.getEmissionSources();
//            diet = (Diet) emissionSources.get(0);
//            elec = (HomeEnergy) emissionSources.get(1);
//            gas = (HomeEnergy) emissionSources.get(2);
//            oil = (HomeEnergy) emissionSources.get(3);
//            bus = (Transportation) emissionSources.get(4);
//            car = (Vehicle) emissionSources.get(5);
//
//            verifyEmissionSources();
//
//            assertEquals("UZBEKISTAN", carbonLog.getCountry());
//
//            // check that nextLog id is set correctly
//            CarbonFootprintLog nextLog = new CarbonFootprintLog("ALGERIA");
//            assertEquals(2, nextLog.getId());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void verifyEmissionSources() {
//        // read back and verify sources have expected values
//        try {
//            List<CarbonEmission> carbonEmissions = JsonSimpleReader.readJson(new File("./data/testCarbonEmissionsFile.txt"));
//
//            Diet diet = (Diet) carbonEmissions.get(0);
//            assertEquals(2000 * 365 * (Diet.MEDMEATEATER_EF / 2000), diet.getCarbonEmission());
//            assertEquals(DietType.MEDIUM_MEAT, diet.getDietType());
//            assertEquals(2000, diet.getCalPerDay());
//
//            HomeEnergy electricity = (HomeEnergy) carbonEmissions.get(1);
//            assertEquals(1000 * 12 * HomeEnergy.ELECTRIC_EF, electricity.getCarbonEmission());
//            assertEquals(EnergyType.ELECTRICITY, electricity.getCarbonEmission());
//            assertEquals(1000, electricity.getMonthlyKwh());
//
//            HomeEnergy gas = (HomeEnergy) carbonEmissions.get(2);
//            assertEquals(1000 * 12 * HomeEnergy.GAS_EF, gas.getCarbonEmission());
//            assertEquals(EnergyType.GAS, gas.getCarbonEmission());
//            assertEquals(1000, gas.getMonthlyKwh());
//
//            HomeEnergy oil = (HomeEnergy) carbonEmissions.get(3);
//            assertEquals(1000 * 12 * HomeEnergy.OIL_EF, oil.getCarbonEmission());
//            assertEquals(EnergyType.OIL, oil.getCarbonEmission());
//            assertEquals(1000, oil.getMonthlyKwh());
//
//            Transportation trans = (Transportation) carbonEmissions.get(4);
//            assertEquals(30 * 365 * Transportation.BUS_EF, trans.getCarbonEmission());
//            assertEquals(30, trans.getDistance());
//
//            Vehicle car = (Vehicle) carbonEmissions.get(5);
//            assertEquals((2 * 365 *  Vehicle.MILES_PER_KM / Vehicle.AVG_MPG), car.getCarbonEmission());
//            assertEquals(2, car.getDistance());
//        } catch (IOException | ParseException e) {
//            fail("exception should not have been thrown");
//        }
//    }

}
