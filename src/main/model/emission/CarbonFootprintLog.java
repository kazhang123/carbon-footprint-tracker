package model.emission;

import model.CountryList;
import model.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import persistence.Jsonable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 represents a log tracking different sources of carbon emissions,
 with a list of all sources, and a user country to determine
 statistics of their country
 */
public class CarbonFootprintLog implements Jsonable {
    public static final double CARBON_PER_TREE = 0.06; // amount of Co2 absorbed annually by average tree in tonnes

    private String country;
    private List<CarbonEmission> emissionSources;
    private Date date;

    // EFFECTS: constructs a new carbon footprint log
    public CarbonFootprintLog(String country) {
        this.country = country;
        emissionSources = new ArrayList<>();
        date = new Date();
    }

    // EFFECTS: constructs a carbon footprint log with id, and emission sources
    //          nextLogId is the id of the next log to be constructed
    // NOTE: this constructor is to be used only when constructing a log from data stored in file
    public CarbonFootprintLog(String country, ArrayList<CarbonEmission> emissions, int month, int day, int year) {
        this.country = country;
        emissionSources = emissions;
        this.date = new Date(month, day, year);
    }

    // EFFECTS: returns list of emission sources
    public List<CarbonEmission> getEmissionSources() {
        return emissionSources;
    }

    // EFFECTS: returns the date the carbon footprint log was recorded
    public Date getDate() {
        return date;
    }

    // EFFECTS: returns the total tonnes of CO2e emitted
    public double getTotalEmission() {
        double total = 0;

        for (CarbonEmission c : emissionSources) {
            total += c.getCarbonEmission();
        }
        return total;
    }

    // EFFECTS: returns country of user
    public String getCountry() {
        return country;
    }

    // MODIFIES: this
    // EFFECTS: sets user country
    public void setCountry(String country) {
        this.country = country;
    }

    // MODIFIES: this
    // REQUIRES: carbon footprint source must not already exist in carbon footprint log
    // EFFECTS: adds new carbon footprint source to log
    public void addCarbonSource(CarbonEmission source) {
        emissionSources.add(source);
    }


    // EFFECTS: returns average annual carbon footprint for user's country
    public double getAvgCountryFootprint() {
        HashMap<String, Double> countryList = CountryList.getInstance().getCountries();
        return countryList.get(country);
    }

    // EFFECTS: returns the percentage the Carbon footprint source makes up of the total carbon footprint
    public int percentageEmission(CarbonEmission source) {
        return (int) Math.round(source.getCarbonEmission() / getTotalEmission() * 100);
    }

    // EFFECTS: returns the number of trees needed to offset total carbon footprint
    public int numTreesToOffset(double emission) {
        return (int) Math.round(emission / CARBON_PER_TREE);
    }

    // EFFECTS: saves this carbon footprint log to file
    @Override
    public void saveJson(FileWriter fileWriter, Object obj) throws IOException {
        JSONArray jsonLogs = (JSONArray) obj;

        JSONObject log = new JSONObject();
        log.put("country", country);
        log.put("month", date.getMonth());
        log.put("day", date.getDay());
        log.put("year", date.getYear());

        JSONArray emissions = new JSONArray();
        for (CarbonEmission c : emissionSources) {
            c.saveJson(fileWriter, emissions);
        }

        log.put("emissionSources", emissions);
        jsonLogs.add(log);
        JSONObject savedObj = new JSONObject();
        savedObj.put("logs", jsonLogs);


        fileWriter.write(savedObj.toJSONString());

    }

}
