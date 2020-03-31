package model.emission;


// represents public transportation as a source of carbon emission,
// with a daily distance travelled on bus and annual carbon emission
public class Transportation extends Travel implements CarbonEmission {

    // Emission factors for public transportation measured in tonnes CO2e / km
    public static final double BUS_EF = 0.00011;

    private double carbonEmission;

    // EFFECTS: constructs a new transportation emission source
    public Transportation() {
        super();
        carbonEmission = 0;
    }

    @Override
    public double getCarbonEmission() {
        return carbonEmission;
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


}
