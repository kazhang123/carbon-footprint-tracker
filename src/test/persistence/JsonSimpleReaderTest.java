package persistence;

import model.emission.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// source: TellerApp
public class JsonSimpleReaderTest {

    @Test
    public void testParseJsonFile1() {
        try {
            List<CarbonFootprintLog> carbonLogs = JsonSimpleReader.readJson(new File("data/testJson1.json"));
            CarbonFootprintLog carbonLog = carbonLogs.get(0);
            ArrayList<CarbonEmission> carbonEmissions = (ArrayList<CarbonEmission>) carbonLog.getEmissionSources();

            assertEquals("ALGERIA", carbonLog.getCountry());

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

    @Test
    public void testJsonFile2() {
        try {
            List<CarbonFootprintLog> carbonLogs = JsonSimpleReader.readJson(new File("data/testJson2.json"));
            assertEquals(2, carbonLogs.size());

            CarbonFootprintLog carbonLog = carbonLogs.get(1);
            ArrayList<CarbonEmission> carbonEmissions = (ArrayList<CarbonEmission>) carbonLog.getEmissionSources();

            assertEquals("ALGERIA", carbonLog.getCountry());

            Diet diet = (Diet) carbonEmissions.get(0);
            assertEquals(DietType.VEGAN, diet.getDietType());
            assertEquals(3000, diet.getCalPerDay());

            HomeEnergy electricity = (HomeEnergy) carbonEmissions.get(1);
            assertEquals(800, electricity.getMonthlyKwh());

            HomeEnergy gas = (HomeEnergy) carbonEmissions.get(2);
            assertEquals(800, gas.getMonthlyKwh());

            HomeEnergy oil = (HomeEnergy) carbonEmissions.get(3);
            assertEquals(800, oil.getMonthlyKwh());

            Transportation trans = (Transportation) carbonEmissions.get(4);
            assertEquals(40, trans.getDistance());

            Vehicle car = (Vehicle) carbonEmissions.get(5);
            assertEquals(10, car.getDistance());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        } catch (ParseException e) {
            fail("exception should not have been thrown");
        }
    }


    @Test
    public void testIOException() {
        try {
            JsonSimpleReader.readJson(new File("./does/not/exist/testJsonFile.txt"));
        } catch (IOException e) {
            // expected
        } catch (ParseException e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void testConstructor() {
        JsonSimpleReader reader = new JsonSimpleReader();
    }
}
