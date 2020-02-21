package persistence;

import model.emission.CarbonEmission;
import model.emission.CarbonFootprintLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// source: TellerApp
// a reader that can read account data form a file
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a carbon footprint log parsed from file
    //          throws an IO exception if an exception is raised when opening/reading from file
    public static CarbonFootprintLog readCarbonFootprintLog(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseFootprintLogContent(fileContent);
    }

    // EFFECTS: returns a list of carbon emissions parsed from file
    //          throws an IO exception if an exception is raised when opening/reading from file
    public static List<CarbonEmission> readCarbonEmissions(File file) throws IOException {
        return null;
    }

    // EFFECTS: returns content of file as list of strings
    //          each string contains content for one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns list of emissions for a carbon log from list of strings
    //          each string contains data for one emission
    private static List<CarbonEmission> parseEmissionContent(List<String> fileContent) {
        return null;
    }

    private static CarbonFootprintLog parseFootprintLogContent(List<String> fileContent) {
        CarbonFootprintLog carbonLog = new CarbonFootprintLog();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            carbonLog = parseFootprintLog(lineComponents);
        }

        return carbonLog;
    }

    // EFFECTS: returns list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components is of size 3 where
    //           element 0 represents the id of the next carbon footprint log to be constructed
    //           element 1 represents the id
    //           element 2 represents the country
    // EFFECTS: returns a carbon footprint log constructed from components
    private static CarbonFootprintLog parseFootprintLog(List<String> components) {
        int nextId = Integer.parseInt(components.get(0));
        int id = Integer.parseInt(components.get(1));
        String country = components.get(2);
        return null;
    }

    // REQUIRES: components is of size 5 where
    //           element 0 represents the id of the next carbon footprint log to be constructed
    //           element 1 represents the id
    //           element 2 represents the annual carbon emission
    //           element 3 represents the emission type (one of VEGETARIAN, VEGAN, PESCETARIAN,
    //           LOW_MEAT, MEDIUM_MEAT, HIGH_MEAT, ELECTRICITY, GAS, OIL)
    //           element 4 represents an amount (kwh, distance, cals)
    // EFFECTS: returns a carbon emission source constructed from components
    private static CarbonEmission parseCarbonEmission(List<String> components) {
        return null;
    }


}
