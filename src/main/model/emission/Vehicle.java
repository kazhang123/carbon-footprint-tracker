package model.emission;

import model.CarbonFootprintLog;
import model.emission.exception.NegativeAmountException;

// represents a personal vehicle as a source of carbon emission,
// with a daily distance travelled on bus and annual carbon emission
public class Vehicle extends CarbonFootprint {

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

    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in
    //          tonnes of carbon dioxide equivalent based on km spent in car a day
    @Override
    public void calculateCarbonEmission(double dailyDistance) {
        carbonEmission = (dailyDistance * 365 * MILES_PER_KM / AVG_MPG) * GASOLINE_EF;
    }

    // EFFECTS: returns a string representation of vehicle's carbon emissions
    @Override
    public String toString() {
        return "Vehicle: " + String.format("%.2f", getCarbonFootprint());
    }

    // EFFECTS: returns the distance travelled by car a day
    public double getDistance() {
        return dailyDistance;
    }

    // MODIFIES: this
    // EFFECTS: sets km travelled by car per day
    public void setDistancePerDay(double distance) throws NegativeAmountException {
        if (distance < 0) {
            throw new NegativeAmountException();
        }
        dailyDistance = distance;
        calculateCarbonEmission(dailyDistance);
    }




}
