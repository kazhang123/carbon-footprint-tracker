package model.emission;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

// represents a personal vehicle as a source of carbon emission,
// with a daily distance travelled on bus and annual carbon emission
public class Vehicle extends CarbonEmission {

    // emission factor in tonnes of CO2e per gallon of gasoline consumed
    public static final double GASOLINE_EF = 0.008887;
    // average mpg (miles per gallon) for cars
    public static final double AVG_MPG = 24;
    public static final double MILES_PER_KM = 0.62;

    private double dailyDistance; // in km

    public Vehicle() {
        super();
        dailyDistance = 0;
    }

    // REQUIRES; dailyDistance >= 0
    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in
    //          tonnes of carbon dioxide equivalent based on km spent in car a day
    @Override
    public void calculateCarbonEmission(double dailyDistance) {
        carbonEmission = (dailyDistance * 365 * MILES_PER_KM / AVG_MPG) * GASOLINE_EF;
        setDistancePerDay(dailyDistance);
    }

    // EFFECTS: returns a string representation of vehicle's carbon emissions
    @Override
    public String toString() {
        return "Vehicle: " + String.format("%.2f", getCarbonEmission());
    }

    @Override
    public double getMax() {
        return 200;
    }

    // EFFECTS: returns the distance travelled by car a day
    public double getDistance() {
        return dailyDistance;
    }

    // REQUIRES: distance >= 0
    // MODIFIES: this
    // EFFECTS: sets km travelled by car per day
    private void setDistancePerDay(double distance) {
        dailyDistance = distance;
    }


    // EFFECTS: saves vehicle object to JSON array
    @Override
    public void saveJson(FileWriter fileWriter, Object obj) {
        JSONObject carObj = new JSONObject();
        carObj.put("label", "Vehicle");
        carObj.put("dailyDistance", dailyDistance);
        JSONArray emissions = (JSONArray) obj;
        emissions.add(carObj);
    }


}
