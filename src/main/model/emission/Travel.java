package model.emission;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

// represents a source of carbon emission via travel
public abstract class Travel {
    protected double dailyDistance; // in km

    public Travel() {
        dailyDistance = 0;
    }

    // EFFECTS: returns maximum daily distance user can select
    public double getMax() {
        return 200;
    }

    // EFFECTS: returns the daily distance
    public double getValue() {
        return dailyDistance;
    }

    // EFFECTS: returns the distance spent on travel each day
    public double getDistance() {
        return dailyDistance;
    }

    // REQUIRES: distance >= 0
    // MODIFIES: this
    // EFFECTS: sets km travelled per day
    protected void setDistancePerDay(double distance)  {
        dailyDistance = distance;
    }

    // EFFECTS: saves transportation object to JSON array
    public void saveJson(FileWriter fileWriter, Object obj) {
        JSONObject travelObj = new JSONObject();
        travelObj.put("label", getLabel());
        travelObj.put("dailyDistance", dailyDistance);
        JSONArray emissions = (JSONArray) obj;
        emissions.add(travelObj);
    }

    protected abstract String getLabel();
}
