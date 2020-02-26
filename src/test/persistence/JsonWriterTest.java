package persistence;

import model.emission.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source: TellerApp
public class JsonWriterTest {
    private static final String TEST_FILE = "data/testJson3.savedLogs.json";
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
    public void testWriteLogs() throws IOException, ParseException {
        writer.write(carbonLog);
        writer.close();

        // verify that the carbon footprint log has expected values
        try {
            List<CarbonFootprintLog> carbonLogs = JsonReader.readJson(new File(TEST_FILE));
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




}
