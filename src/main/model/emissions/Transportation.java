package model.emissions;

public class Transportation extends CarbonFootprint {

    // Emission factors for public transportation measured in tonnes CO2e / km
    public static final double BUS_EF = 0.00011;

    private double dailyDistance; // in km

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
    }

    // EFFECTS: returns the distance spent on bus each day
    public double getDistance() {
        return dailyDistance;
    }

    // MODIFIES: this
    // REQURES: distance >= 0
    // EFFECTS: sets km travelled per day by bus and carbon emission levels based on distance
    public void setDistancePerDay(int distance) {
        dailyDistance = distance;
        calculateCarbonEmission(dailyDistance);
    }


}
