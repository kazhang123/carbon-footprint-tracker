package model;

import model.emissions.CarbonFootprint;

import java.util.ArrayList;
import java.util.List;

public class CarbonFootprintLog {

    public static final double CARBON_PER_TREE = 0.006; // amount of Co2 absorbed anually by average tree in tonnes
    public static final double USA_AVG = 16.49;
    public static final double CAN_AVG = 15.12;
    public static final double WORLD_AVG = 5;

    private String country;
    private List<CarbonFootprint> emissionSources;

    public CarbonFootprintLog(String country) {
        this.country = country;
        emissionSources = new ArrayList<>();
    }

    // EFFECTS: returns the total tonnes of CO2e emitted
    public double getTotalEmission() {
        double total = 0;

        for (CarbonFootprint c : emissionSources) {
            total += c.getCarbonFootprint();
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
    // REQUIRES: source != null
    // EFFECTS: removes source of emission
    public void removeCarbonSource(CarbonFootprint source) {
        emissionSources.remove(source);
    }

    // MODIFIES: this
    // REQUIRES: carbon footprint source must not already exist in carbon footprint log
    // EFFECTS: adds new carbon footprint source if not already in log
    //          if already in log, update existing source with tonnes of CO2e from amt
    public void addCarbonSource(CarbonFootprint source) {
        emissionSources.add(source);
    }


    // EFFECTS: returns average annual carbon footprint for user's country
    public double getAvgCountryFootprint() {
        if (country.equals("Canada")) {
            return CAN_AVG;
        } else if (country.equals("USA")) {
            return USA_AVG;
        } else {
            return WORLD_AVG;
        }
    }

    // EFFECTS: returns the percentage the Carbon footprint source makes up of the total carbon footprint
    public int percentageEmission(CarbonFootprint source) {
        return (int) Math.round(source.getCarbonFootprint() / getTotalEmission() * 100);
    }

    // EFFECTS: returns the number of trees needed to offset total carbon footprint
    public int numTreesToOffset() {
        return (int) Math.round(getTotalEmission() / CARBON_PER_TREE);
    }
}
