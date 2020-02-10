package model.emissions;

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

    // EFFECTS: returns the distance travelled by car a day
    public double getDistance() {
        return dailyDistance;
    }

    // MODIFIES: this
    // REQUIRES: distance >= 0
    // EFFECTS: sets km travelled by car per day
    public void setDistancePerDay(int distance) {
        dailyDistance = distance;
        calculateCarbonEmission(dailyDistance);
    }



}
