package model.emission;

import model.emission.exception.NegativeAmountException;

import java.io.PrintWriter;

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

    // NOTE: only used when constructing a transportation emission source from file
    public Transportation(int nextId, int id, double emission, double distance) {
        super(nextId, id, emission);
        dailyDistance = distance;
    }

    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in
    //          tonnes of carbon dioxide equivalent based on km spent on bus a day
    @Override
    protected void calculateCarbonEmission(double dailyDistance) {
        carbonEmission = dailyDistance * 365 * BUS_EF;
    }

    // EFFECTS: returns a string representation of transportation's carbon emissions
    @Override
    public String toString() {
        return "Transportation: " + String.format("%.2f", getCarbonEmission());
    }

    // EFFECTS: returns the distance spent on bus each day
    public double getDistance() {
        return dailyDistance;
    }

    // MODIFIES: this
    // EFFECTS: sets km travelled per day by bus and carbon emission levels based on distance
    public void setDistancePerDay(double distance) throws NegativeAmountException {
        if (distance < 0) {
            throw new NegativeAmountException();
        }
        dailyDistance = distance;
        calculateCarbonEmission(dailyDistance);
    }


    @Override
    public void save(PrintWriter printwriter) {

    }
}
