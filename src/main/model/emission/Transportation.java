package model.emission;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

// represents public transportation as a source of carbon emission,
// with a daily distance travelled on bus and annual carbon emission
public class Transportation extends CarbonEmission {

    // Emission factors for public transportation measured in tonnes CO2e / km
    public static final double BUS_EF = 0.00011;

    private double dailyDistance; // in km

    // EFFECTS: constructs a new transportation emission source
    public Transportation() {
        super();
        dailyDistance = 0;
    }

    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in
    //          tonnes of carbon dioxide equivalent based on km spent on bus a day
    @Override
    public void calculateCarbonEmission(double dailyDistance) {
        carbonEmission = dailyDistance * 365 * BUS_EF;
        setDistancePerDay(dailyDistance);
    }

    // EFFECTS: returns a string representation of transportation's carbon emissions
    @Override
    public String toString() {
        return "Transportation: " + String.format("%.2f", getCarbonEmission());
    }

    // EFFECTS: returns maximum daily distance user can select
    @Override
    public double getMax() {
        return 200;
    }

    // EFFECTS: returns the daily distance
    @Override
    public double getValue() {
        return dailyDistance;
    }

    // EFFECTS: returns the distance spent on bus each day
    public double getDistance() {
        return dailyDistance;
    }

    // REQUIRES: distance >= 0
    // MODIFIES: this
    // EFFECTS: sets km travelled per day by bus and carbon emission levels based on distance
    private void setDistancePerDay(double distance)  {
        dailyDistance = distance;
    }


    // EFFECTS: saves transportation object to JSON array
    @Override
    public void saveJson(FileWriter fileWriter, Object obj) {
        JSONObject busObj = new JSONObject();
        busObj.put("label", "Transportation");
        busObj.put("dailyDistance", dailyDistance);
        JSONArray emissions = (JSONArray) obj;
        emissions.add(busObj);
    }


}
