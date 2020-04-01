package persistence;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import model.emission.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source: TellerApp
public class JsonWriterTest {
    private static final String TEST_FILE = "data/testJson3.json";
    private JsonWriter writer;
    private CarbonFootprintLog carbonLog;
    private Diet diet;
    private HomeEnergy elec;
    private HomeEnergy gas;
    private HomeEnergy oil;
    private Transportation bus;
    private Vehicle car;

    @BeforeEach
    public void setUp() throws IOException {
        writer = new JsonWriter(new File(TEST_FILE));

        diet = new Diet(DietType.MEDIUM_MEAT);
        elec = new HomeEnergy(EnergyType.ELECTRICITY);
        gas = new HomeEnergy(EnergyType.GAS);
        oil = new HomeEnergy(EnergyType.OIL);
        bus = new Transportation();
        car = new Vehicle();

        diet.calculateCarbonEmission(2000);
        elec.calculateCarbonEmission(1000);
        gas.calculateCarbonEmission(1000);
        oil.calculateCarbonEmission(1000);
        bus.calculateCarbonEmission(40);
        car.calculateCarbonEmission(10);

        List<CarbonEmission> emissionList = Arrays.asList(diet, elec, gas, oil, bus, car);
        ArrayList<CarbonEmission> emissions = new ArrayList<>();
        emissions.addAll(emissionList);

        carbonLog = new CarbonFootprintLog("UZBEKISTAN", emissions, 1, 1, 2020);
    }

    @Test
    public void testWriteLogs() throws IOException {
        writer.write(carbonLog);
        writer.close();

        // verify that the carbon footprint log has expected values
        try {
            List<CarbonFootprintLog> carbonLogs = JsonReader.readJson(new File(TEST_FILE));
            CarbonFootprintLog carbonLog = carbonLogs.get(0);
            ArrayList<CarbonEmission> carbonEmissions = (ArrayList<CarbonEmission>) carbonLog.getEmissionSources();

            assertEquals("UZBEKISTAN", carbonLog.getCountry());
            assertEquals(1, carbonLog.getDate().getMonth());
            assertEquals(2020, carbonLog.getDate().getYear());

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


    // test that a new array list of logs will be created when there are no saved logs
    @Test
    public void testWriteToNewFile() {
        try {
            writer.close();
            PrintWriter pw = new PrintWriter("data/testJson4.json");
            pw.close();

            JsonWriter testWriter = new JsonWriter(new File("data/testJson4.json"));
            testWriter.write(carbonLog);
            testWriter.close();

            List<CarbonFootprintLog> logs = JsonReader.readJson(new File("data/testJson4.json"));
            assertEquals(1, logs.size());

        } catch (FileNotFoundException e) {
            fail("Should not have thrown FileNotFoundException");
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (ParseException e) {
            fail("Should not have thrown ParseException");
        }

    }






}
