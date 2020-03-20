package persistence;

import org.json.simple.parser.JSONParser;
import model.emission.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// source: TellerApp
// a reader that can read carbon footprint log data from a savedLogs.json file
public class JsonReader {

    // EFFECTS: returns a list of carbon footprint logs read from file
    public static List<CarbonFootprintLog> readJson(File file) throws IOException, ParseException {
        JSONArray jsonLogs = getJsonLogs(file);
        ArrayList<CarbonFootprintLog> logs = new ArrayList<>();

        for (Object log : jsonLogs) {
            JSONObject jsonLog = (JSONObject) log;
            String country = (String) jsonLog.get("country");
            int month = (int) (long) jsonLog.get("month");
            int day = (int) (long) jsonLog.get("day");
            int year = (int) (long) jsonLog.get("year");
            JSONArray emissionSources = (JSONArray) jsonLog.get("emissionSources");
            ArrayList<CarbonEmission> emissions = parseEmissionSources(emissionSources);
            CarbonFootprintLog carbonLog = new CarbonFootprintLog(country, emissions, month, day, year);
            logs.add(carbonLog);
        }

        return logs;
    }

    // EFFECTS: returns a list of carbon emission sources for a carbon footprint log read from JSONArray
    public static ArrayList<CarbonEmission> parseEmissionSources(JSONArray sources) {
        JSONObject jsonDiet = (JSONObject) sources.get(0);
        String dietTypeString = (String) jsonDiet.get("dietType");
        Diet diet = new Diet(DietType.valueOf(dietTypeString));
        diet.calculateCarbonEmission((int) (long) jsonDiet.get("cals"));

        HomeEnergy elec = new HomeEnergy(EnergyType.ELECTRICITY);
        JSONObject jsonElec = (JSONObject) sources.get(1);
        elec.calculateCarbonEmission(Double.parseDouble(String.valueOf(jsonElec.get("monthlyKwh"))));

        HomeEnergy gas = new HomeEnergy(EnergyType.GAS);
        JSONObject jsonGas = (JSONObject) sources.get(2);
        gas.calculateCarbonEmission(Double.parseDouble(String.valueOf(jsonGas.get("monthlyKwh"))));

        HomeEnergy oil = new HomeEnergy(EnergyType.OIL);
        JSONObject jsonOil = (JSONObject) sources.get(3);
        oil.calculateCarbonEmission(Double.parseDouble(String.valueOf(jsonOil.get("monthlyKwh"))));

        Transportation bus = new Transportation();
        JSONObject jsonBus = (JSONObject) sources.get(4);
        bus.calculateCarbonEmission(Double.parseDouble(String.valueOf(jsonBus.get("dailyDistance"))));

        Vehicle car = new Vehicle();
        JSONObject jsonCar = (JSONObject) sources.get(5);
        car.calculateCarbonEmission(Double.parseDouble(String.valueOf(jsonCar.get("dailyDistance"))));

        List<CarbonEmission> emissionList = Arrays.asList(diet, elec, gas, oil, bus, car);
        ArrayList<CarbonEmission> emissionArrayList = new ArrayList<>();
        emissionArrayList.addAll(emissionList);

        return emissionArrayList;
    }

    // EFFECTS: returns a JSONArray of carbon footprint logs read from file
    protected static JSONArray getJsonLogs(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jsonLogs = (JSONArray) jsonObj.get("logs");
        return jsonLogs;
    }



}
