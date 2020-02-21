package persistence;

import model.emission.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {

    @Test
    public void testParseEmissionsFile() {
        try {
            List<CarbonEmission> carbonEmissions = Reader.readCarbonEmissions(new File("./data/testCarbonEmissionsFile.txt"));

            Diet diet = (Diet) carbonEmissions.get(0);
            assertEquals(10, diet.getCarbonEmission());
            assertEquals(DietType.VEGAN, diet.getDietType());
            assertEquals(2000, diet.getCalPerDay());

            HomeEnergy electricity = (HomeEnergy) carbonEmissions.get(1);
            assertEquals(20, electricity.getCarbonEmission());
            assertEquals(EnergyType.ELECTRICITY, electricity.getEnergyType());
            assertEquals(1000, electricity.getMonthlyKwh());

            HomeEnergy gas = (HomeEnergy) carbonEmissions.get(2);
            assertEquals(30, gas.getCarbonEmission());
            assertEquals(EnergyType.GAS, gas.getEnergyType());
            assertEquals(1000, gas.getMonthlyKwh());

            HomeEnergy oil = (HomeEnergy) carbonEmissions.get(3);
            assertEquals(40, oil.getCarbonEmission());
            assertEquals(EnergyType.OIL, oil.getEnergyType());
            assertEquals(1000, oil.getMonthlyKwh());

            Transportation trans = (Transportation) carbonEmissions.get(4);
            assertEquals(50, trans.getCarbonEmission());
            assertEquals(30, trans.getDistance());

            Vehicle car = (Vehicle) carbonEmissions.get(5);
            assertEquals(60, car.getCarbonEmission());
            assertEquals(40, car.getDistance());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void parseCarbonFootprintLogFile() {
        CarbonFootprintLog carbonLog = null;
        try {
            carbonLog = Reader.readCarbonFootprintLog(new File("./data/testCarbonEmissionsFile.txt"));
            assertEquals("CANADA", carbonLog.getCountry());
            assertEquals(200, carbonLog.getTotalEmission());

            // check that nextLog id is set correctly
            CarbonFootprintLog nextLog = new CarbonFootprintLog("ALGERIA");
            assertEquals(2, nextLog.getId());
        } catch (IOException e) {
            fail("exception should not have been thrown");
        }

    }

    @Test
    public void testIOException() {
        try {
            Reader.readCarbonEmissions(new File("./does/not/exist/testCarbonEmissionsFile.txt"));
        } catch (IOException e) {
            // expected
        }

        try {
            Reader.readCarbonFootprintLog(new File("./does/not/exist/testCarbonEmissionsFile.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
