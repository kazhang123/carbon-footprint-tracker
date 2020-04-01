package model.emission;



// represents a personal vehicle as a source of carbon emission,
// with a daily distance travelled on bus and annual carbon emission
public class Vehicle extends Travel implements CarbonEmission {

    // emission factor in tonnes of CO2e per gallon of gasoline consumed
    public static final double GASOLINE_EF = 0.008887;
    // average mpg (miles per gallon) for cars
    public static final double AVG_MPG = 24;
    public static final double MILES_PER_KM = 0.62;

    private double carbonEmission;

    // EFFECTS: constructs a personal vehicle emission source
    public Vehicle() {
        super();
        carbonEmission = 0;
    }

    // EFFECTS: returns label to be used when saving object to file
    @Override
    protected String getLabel() {
        return "Vehicle";
    }

    // EFFECTS: returns the carbon emission in tonnes of CO2e
    @Override
    public double getCarbonEmission() {
        return carbonEmission;
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



}
